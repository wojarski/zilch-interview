package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.program.stage.AbstractStage;
import com.zilch.washingmachine.program.stage.DrainStage;
import com.zilch.washingmachine.program.stage.RinseStage;
import com.zilch.washingmachine.program.stage.SoakStage;
import com.zilch.washingmachine.program.stage.WashStage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ECOProgram extends AbstractProgram {
    private ProgramConfig programConfig;

    @Override
    public List<AbstractStage> getStages() {
        AbstractStage soakStage = SoakStage.builder().build();
        AbstractStage washStage = WashStage.builder().build();
        AbstractStage rinseStage = RinseStage.builder().build();
        AbstractStage drainStage = DrainStage.builder().build();
        return List.of(soakStage, washStage, rinseStage, drainStage);
    }

    // TODO improve so default Config is consistent with stages;
    @Override
    public ProgramConfig getProgramDefaultConfig() {
        return ProgramConfig.builder()
                .stageConfig(new HashMap<>(Map.of(
                        StageType.SOAK, new HashMap<>(Map.of(ConfigType.DURATION, "10")),
                        StageType.WASH, new HashMap<>(Map.of(ConfigType.DURATION, "25", ConfigType.TEMPERATURE, "30")),
                        StageType.RINSE, new HashMap<>(Map.of(ConfigType.DURATION, "10")),
                        StageType.DRAIN, new HashMap<>(Map.of(ConfigType.DURATION, "10")))))
                .stageActivityConfig(new HashMap<>(Map.of(StageActivityType.HEAT_UP, Map.of(ConfigType.TEMPERATURE, "30"))))
                .build();
    }
}
