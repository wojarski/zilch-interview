package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.model.StageType;
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
                .stageConfig(Map.of(
                        StageType.SOAK, Map.of(ConfigType.DURATION, "10"),
                        StageType.WASH, Map.of(ConfigType.DURATION, "25", ConfigType.TEMPERATURE, "30"),
                        StageType.RINSE, Map.of(ConfigType.DURATION, "10"),
                        StageType.DRAIN, Map.of(ConfigType.DURATION, "10")))
                .subStageConfig(Map.of(StageActivityType.HEAT_UP, Map.of(ConfigType.TEMPERATURE, null)))
                .build();
    }
}
