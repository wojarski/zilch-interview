package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.SubStageType;
import com.zilch.washingmachine.model.StageType;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RinseStage extends AbstractStage {

    Duration duration;

    @Override
    public StageType getType() {
        return StageType.RINSE;
    }

    @Override
    public List<SubStageType> getSubStages() {
        return null;
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