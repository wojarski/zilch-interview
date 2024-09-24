package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.program.AbstractStage;

public class StageProcessor {
    public void run(AbstractStage stage) {
        stage.getSubStages().getFirst();
    }
}
