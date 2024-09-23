package com.zilch.washingmachine.model;

import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DrainStage extends AbstractStage {
    Duration duration;

    @Override
    public StageType getType() {
        return StageType.DRAIN;
    }

    @Override
    public List<SubStageType> getSubStages() {
        return List.of(SubStageType.SPIN, SubStageType.PUMP);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of(StageType.SOAK, StageType.WASH);
    }

    @Override
    public boolean canPause() {
        return false;
    }

}
