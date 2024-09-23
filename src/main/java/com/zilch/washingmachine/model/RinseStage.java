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
public class RinseStage extends AbstractStage {
    Duration duration;

    @Override
    public StageType getType() {
        return StageType.RINSE;
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of(StageType.SOAK, StageType.WASH);
    }
}
