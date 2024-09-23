package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.model.AbstractStage;

public class StageProcessor {
    public void run(AbstractStage stage) {
        stage.getSubStages().get(0);
    }
}
