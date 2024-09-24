package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.SubStageType;
import com.zilch.washingmachine.model.stage.Stage;
import com.zilch.washingmachine.model.StageType;
import java.util.List;

public abstract class AbstractStage {
    private Stage stageState;

    public abstract StageType getType();
    public abstract List<SubStageType> getSubStages();
    public abstract List<StageType> getAllowedPredecessors();

    //probably this does not belong here
    public abstract boolean canPause();

    public Stage getStageState() {
        return stageState;
    }

}
