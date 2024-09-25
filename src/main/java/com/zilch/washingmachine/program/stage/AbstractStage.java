package com.zilch.washingmachine.program.stage;

import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.StageActivityType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractStage {
    protected Stage stage;
    public abstract StageType getType();
    public abstract List<StageActivityType> getSubStages();
    public abstract List<StageType> getAllowedPredecessors();
}
