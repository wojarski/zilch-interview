package com.zilch.washingmachine.model;

import java.util.List;

public abstract class AbstractStage {
    public abstract StageType getType();
    public abstract List<SubStageType> getSubStages();
    public abstract List<StageType> getAllowedPredecessors();
    public abstract boolean canPause();
}
