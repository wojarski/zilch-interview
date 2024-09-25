package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.program.stage.AbstractStage;
import com.zilch.washingmachine.program.stage.DrainStage;
import com.zilch.washingmachine.program.stage.RinseStage;
import com.zilch.washingmachine.program.stage.WashStage;
import java.io.ObjectInputFilter.Config;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.zilch.washingmachine.model.ProgramConfig.ConfigType.*;
import static com.zilch.washingmachine.model.StageActivityType.*;

@Builder
@Setter
@Getter
public class QuickProgram extends AbstractProgram {
    private ProgramConfig programConfig;

    @Override
    public List<AbstractStage> getStages() {
        AbstractStage washStage = WashStage.builder().build();
        AbstractStage rinseStage = RinseStage.builder().build();
        AbstractStage drainStage = DrainStage.builder().build();
        return List.of(washStage, rinseStage, drainStage);
    }

    @Override
    public ProgramConfig getProgramDefaultConfig() {
        return ProgramConfig.builder()
                .stageConfig(new HashMap<>(Map.of(
                        StageType.WASH, new HashMap<>(Map.of(MAX_DURATION, "12")),
                        StageType.RINSE, new HashMap<>(Map.of(MAX_DURATION, "10")),
                        StageType.DRAIN, new HashMap<>(Map.of(REPEATS, "10"))
                )))
                .stageActivityConfig(new HashMap<>(Map.of(
                        StageActivityType.HEAT_UP, new HashMap<>(Map.of(TEMPERATURE, "40")),
                        StageActivityType.SPIN, new HashMap<>(Map.of(REPEATS, "2")),
                        StageActivityType.IDLE, new HashMap<>(Map.of(REPEATS, "2"))
                )))
                .build();
    }
}
