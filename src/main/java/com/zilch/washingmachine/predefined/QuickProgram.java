package com.zilch.washingmachine.predefined;

import com.zilch.washingmachine.model.AbstractStage;
import com.zilch.washingmachine.model.DrainStage;
import com.zilch.washingmachine.model.RinseStage;
import com.zilch.washingmachine.model.WashStage;
import java.time.Duration;
import java.util.List;

public class QuickProgram implements AbstractProgram {
    @Override
    public List<AbstractStage> getStages() {
        AbstractStage washStage = WashStage.builder().repeats(1).build();
        AbstractStage rinseStage = RinseStage.builder().duration(Duration.ofMinutes(10)).build();
        AbstractStage drainStage = DrainStage.builder().duration(Duration.ofMinutes(5)).build();
        return List.of(washStage, rinseStage, drainStage);
    }
}
