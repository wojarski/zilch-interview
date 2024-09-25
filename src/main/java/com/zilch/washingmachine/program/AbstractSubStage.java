package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.StageActivity;
import com.zilch.washingmachine.model.StageActivityType;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class AbstractSubStage {
    private StageActivity state;

    public abstract StageActivityType getType();
    public abstract List<StageActivityType> getSubStages();
    public abstract List<StageType> getAllowedPredecessors();

    //probably this does not belong here
    public abstract boolean canPause();

}
