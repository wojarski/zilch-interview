package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.Operation;
import com.zilch.washingmachine.model.OperationStatus;
import com.zilch.washingmachine.program.AbstractStage;
import com.zilch.washingmachine.persistence.LaundryMapper;
import com.zilch.washingmachine.persistence.LaundryRepository;
import com.zilch.washingmachine.persistence.StageMapper;
import com.zilch.washingmachine.persistence.StageRepository;
import com.zilch.washingmachine.program.AbstractProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaundryRunner {
    @Autowired
    StageProcessor stageProcessor;

    @Autowired
    LaundryRepository laundryRepository;
    @Autowired
    StageRepository stageRepository;

    public Operation run(Laundry laundry) {
        validate(laundry);

        AbstractProgram program = laundry.getProgram();
        AbstractStage stage = laundry.getStage();

        laundryRepository.save(LaundryMapper.INSTANCE.mapToEntity(laundry));
        stageRepository.save(StageMapper.INSTANCE.mapToEntity(stage.getStageState()));

        stageProcessor.run(stage);

        return new Operation(OperationStatus.SUCCESS, laundry.getId());
    }

    private void validate(Laundry laundry) {
        // TODO any validations
    }

}
