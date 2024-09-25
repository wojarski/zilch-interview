package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.Operation;
import com.zilch.washingmachine.model.OperationStatus;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.persistence.LaundryMapper;
import com.zilch.washingmachine.persistence.LaundryRepository;
import com.zilch.washingmachine.persistence.StageMapper;
import com.zilch.washingmachine.persistence.StageRepository;
import com.zilch.washingmachine.program.LaundryFactory;
import com.zilch.washingmachine.program.stage.AbstractStage;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
@Transactional
public class LaundryService {
    @Autowired
    private LaundryFactory laundryFactory;
    @Autowired
    private StageProcessor stageProcessor;
    @Autowired
    private LaundryRepository laundryRepository;
    @Autowired
    private StageRepository stageRepository;
    @Autowired
    private DeviceOutboxService deviceOutboxService;

    public Operation startNewLaundry(Laundry laundry) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                deviceOutboxService.performActions();
            }
        });
        validate(laundry);

        AbstractStage stage = laundry.getStage();
        ProgramConfig config = laundry.getProgram().getProgramConfig();
        stageProcessor.run(stage, config);

        laundryRepository.save(LaundryMapper.INSTANCE.mapToEntity(laundry));
        stageRepository.save(StageMapper.INSTANCE.mapToEntity(stage.getStage()));

        return new Operation(OperationStatus.SUCCESS, laundry.getId());
    }


    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doAfterCommit(ApplicationEvent event){
        log.info("before commit!");

        //deviceFacade.pourWater();

        // entity.setCompleted(true);
        // deviceOutboxRepository.save(entity);
    }

    @SuppressWarnings("all")
    public Operation continueLaundry(UUID laundryId) {
        return laundryRepository.findById(laundryId).map(LaundryMapper.INSTANCE::mapFromEntity)
                .map(this::continueLaundry0)
                .orElseGet(() -> new Operation(OperationStatus.FAILURE, null,
                           String.format("Could not find laundry object for id %s", laundryId)));
    }

    @SuppressWarnings("all")
    public Operation continueLaundry(String serialNumber) {
        return laundryRepository.findByDeviceSerialNumber(serialNumber)
                .map(LaundryMapper.INSTANCE::mapFromEntity)
                .map(this::continueLaundry0)
                .orElseGet(() -> new Operation(OperationStatus.FAILURE, null,
                           String.format("Could not find laundry object for device %s", serialNumber)));
    }

    private Operation continueLaundry0(Laundry laundry) {
        AbstractStage stage = laundry.getStage();
        ProgramConfig config = laundry.getProgram().getProgramConfig();

        if (isFinished(stage)) {
            List<Stage> stages = new ArrayList<>(laundry.getProcessedStages());
            stages.add(stage.getStage());
            laundry.setProcessedStages(stages);
        } else {
            stageProcessor.run(stage, config);
        }

        Optional<AbstractStage> oNextStage = findNextStage(laundry);
        if (oNextStage.isEmpty()) {
            return new Operation(OperationStatus.SUCCESS, null, String.format("Laundry %s finished", laundry.getId()));
        }

        AbstractStage nextStage = oNextStage.get();
        nextStage.setStage(laundryFactory.newStage(nextStage, laundry.getId()));
        laundry.setStage(nextStage);
        stageProcessor.run(nextStage, config);

        laundryRepository.save(LaundryMapper.INSTANCE.mapToEntity(laundry));
        stageRepository.save(StageMapper.INSTANCE.mapToEntity(stage.getStage()));

        return new Operation(OperationStatus.SUCCESS, laundry.getId());
    }

    private boolean isFinished(AbstractStage stage) {
        return stage.getStage().getFinishedAt() != null;
    }

    private Optional<AbstractStage> findNextStage(Laundry laundry) {
        List<AbstractStage> allStagesToProcess = laundry.getProgram().getStages();
        List<StageType> processedTypes = laundry.getProcessedStages().stream().map(Stage::getType).collect(Collectors.toList());

        return allStagesToProcess.stream().filter(stage -> {
            StageType processedType = stage.getStage().getType();
            boolean processed = processedTypes.contains(processedType);
            processedTypes.remove(processedType);
            return processed;
        }).findFirst();
    }

    // TODO any validations for a new Laundry
    private void validate(Laundry laundry) {

    }

    public List<Laundry> listAllLaundries() {
        return laundryRepository.findAll().stream().map(LaundryMapper.INSTANCE::mapFromEntity).toList();
    }
}
