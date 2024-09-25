package com.zilch.washingmachine.program.stage;

import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.model.StageType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
@AllArgsConstructor
public class WashStage extends AbstractStage {
    @Override
    public StageType getType() {
        return StageType.WASH;
    }

    @Override
    public List<StageActivityType> getSubStages() {
        return List.of(StageActivityType.SPIN, StageActivityType.IDLE);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of(StageType.SOAK, StageType.RINSE);
    }
}
