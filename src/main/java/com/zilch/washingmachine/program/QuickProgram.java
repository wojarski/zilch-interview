package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.SubStageType;
import com.zilch.washingmachine.model.WashStage;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class QuickProgram extends AbstractProgram {
    private ProgramConfig programConfig;
    @Override
    public List<AbstractStage> getStages() {
        AbstractStage washStage = WashStage.builder().repeats(1).build();
        AbstractStage rinseStage = RinseStage.builder().duration(Duration.ofMinutes(10)).build();
        AbstractStage drainStage = DrainStage.builder().duration(Duration.ofMinutes(5)).build();
        return List.of(washStage, rinseStage, drainStage);
    }

    // TODO improve so default Config is consistent with stages;
    @Override
    public ProgramConfig getProgramDefaultConfig() {
        return ProgramConfig.builder()
                .stageConfig(Map.of(
                        StageType.WASH, Map.of(ConfigType.DURATION, "10"),
                        StageType.RINSE, Map.of(ConfigType.DURATION, "10"),
                        StageType.DRAIN, Map.of(ConfigType.DURATION, "10")))
                .subStageConfig(Map.of(SubStageType.HEAT_UP, Map.of(ConfigType.TEMPERATURE, null)))
                .build();
    }
}
