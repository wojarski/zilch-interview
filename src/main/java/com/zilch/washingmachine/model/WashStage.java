package com.zilch.washingmachine.model;

import com.zilch.washingmachine.model.AbstractStage;
import com.zilch.washingmachine.model.StageType;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class WashStage extends AbstractStage {
    Duration duration;
    Integer repeats;

    @Override
    public StageType getType() {
        return StageType.WASH;
    }

    @Override
    public List<SubStageType> getSubStages() {
        return List.of(SubStageType.SPIN, SubStageType.IDLE);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of(StageType.SOAK, StageType.RINSE);
    }

    @Override
    public boolean canPause() {
        return false;
    }
}
