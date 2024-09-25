package com.zilch.washingmachine.program.stage;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.StageActivityType;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
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
    public abstract List<StageActivityType> getActivities();
    public List<StageActivityType> getEffectiveActivities(ProgramConfig config) {
        return getActivities();
    }
    public abstract List<StageType> getAllowedPredecessors();
}
