package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.StageActivity;
import com.zilch.washingmachine.model.StageActivityType;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LaundryFactory {
    public Laundry newLaundry(AbstractProgram program, ProgramConfig userConfig) {
        AbstractStage abstractStage = program.getStages().getFirst();
        UUID laundryId = UUID.randomUUID();
        AbstractStage stage = abstractStage.toBuilder().stage(newStage(abstractStage, laundryId)).build();

        ProgramConfig defaultConfig = program.getProgramDefaultConfig();
        ProgramConfig config = configure(defaultConfig, userConfig);
        program.setProgramConfig(config);

        return Laundry.builder()
                .id(laundryId)
                .program(program)
                .stage(stage)
                .processedStages(new ArrayList<>())
                .build();
    }

    public Stage newStage(AbstractStage stage, UUID laundryId) {
        StageActivityType subStageType = stage.getSubStages().getFirst();
        UUID stageId = UUID.randomUUID();
        return Stage.builder()
                .id(stageId)
                .laundryId(laundryId)
                .type(stage.getType())
                .activity(newSubStage(subStageType, stageId))
                .processedActivities(new ArrayList<>())
                .build();
    }

    public StageActivity newSubStage(StageActivityType type, UUID stageId) {
        return StageActivity.builder()
                .id(UUID.randomUUID())
                .stageId(stageId)
                .type(type)
                .build();
    }

    private ProgramConfig configure(ProgramConfig defaultConfig, ProgramConfig userConfig) {
        Map<StageType, Map<ConfigType, String>> config = defaultConfig.getStageConfig();

        config.keySet().forEach(stageType -> {
            config.merge(stageType, userConfig.getStageConfig().get(stageType), (defaultStageConfig, userStageConfig) -> {
                defaultStageConfig.keySet().forEach(configType -> {
                    defaultStageConfig.merge(configType, userStageConfig.get(configType), (defaultConfigValue, userConfigValue) -> defaultConfigValue);
                });
                return defaultStageConfig;
            });
        });

        return defaultConfig;
    }
}
