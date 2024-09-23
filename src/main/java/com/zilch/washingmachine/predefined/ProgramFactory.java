package com.zilch.washingmachine.predefined;

import com.zilch.washingmachine.model.AbstractStage;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.ProgramConfig;

public class ProgramFactory {

    public Laundry newLaundry(AbstractProgram program, ProgramConfig config) {
        AbstractStage stage = program.getStages().getFirst();

        Laundry laundry = Laundry.builder().stage(stage).build();

        return laundry;
    }

}
