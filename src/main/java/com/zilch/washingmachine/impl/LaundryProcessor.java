package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.model.AbstractStage;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.persistence.LaundryMapper;
import com.zilch.washingmachine.persistence.StageRepository;
import com.zilch.washingmachine.predefined.AbstractProgram;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaundryProcessor {
    @Autowired
    StageProcessor stageProcessor;

    @Autowired
    StageRepository stageRepository;

    public UUID run(AbstractProgram program) {
        AbstractStage stage = program.getStages().get(0);

        Laundry laundry = null;

        stageRepository.save(LaundryMapper.INSTANCE.mapToEntity(laundry));


        stageProcessor.run(stage);

        return UUID.randomUUID();
    }

}
