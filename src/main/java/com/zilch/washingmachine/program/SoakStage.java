package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.model.StageType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Builder
@Slf4j
public class SoakStage extends AbstractStage {
    @Override
    public StageType getType() {
        return StageType.SOAK;
    }

    @Override
    public List<StageActivityType> getSubStages() {
        return List.of(StageActivityType.POUR_WATER, StageActivityType.IDLE);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of();
    }

    @Override
    public boolean canPause() {
        return false;
    }

}
