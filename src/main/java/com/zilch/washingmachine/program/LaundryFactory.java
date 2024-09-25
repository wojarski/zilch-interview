package com.zilch.washingmachine.program;

import com.zilch.washingmachine.device.DummyDeviceConnector;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.model.StageActivity;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.program.stage.AbstractStage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LaundryFactory {
    public Laundry newLaundry(AbstractProgram program, ProgramConfig userConfig) {
        AbstractStage stage = program.getStages().getFirst();
        UUID laundryId = UUID.randomUUID();
        stage.setStage(newStage(stage, laundryId));

        ProgramConfig defaultConfig = program.getProgramDefaultConfig();
        ProgramConfig config = configure(defaultConfig, userConfig);
        program.setProgramConfig(config);

        return Laundry.builder()
                .id(laundryId)
                .program(program)
                .stage(stage)
                .processedStages(new ArrayList<>())
                .deviceSerialNumber(DummyDeviceConnector.DEFAULT_SERIAL_NUMBER)
                .build();
    }

    public Stage newStage(AbstractStage stage, UUID laundryId) {
        StageActivityType subStageType = stage.getActivities().getFirst();
        UUID stageId = UUID.randomUUID();
        return Stage.builder()
                .id(stageId)
                .laundryId(laundryId)
                .type(stage.getType())
                .activity(newStageActivity(subStageType, stageId))
                .processedActivities(new ArrayList<>())
                .build();
    }

    public StageActivity newStageActivity(StageActivityType type, UUID stageId) {
        return StageActivity.builder()
                .id(UUID.randomUUID())
                .stageId(stageId)
                .type(type)
                .build();
    }

    private ProgramConfig configure(ProgramConfig defaultConfig, ProgramConfig userConfig) {
        Map<StageType, Map<ConfigType, String>> config = defaultConfig.getStageConfig();

        config.keySet().forEach(stageType -> {
            if (userConfig.getStageConfig().get(stageType) != null) {
                config.merge(stageType, userConfig.getStageConfig().get(stageType), (defaultStageConfig, userStageConfig) -> {
                    defaultStageConfig.keySet().forEach(configType -> {
                        defaultStageConfig.merge(configType, userStageConfig.get(configType), (defaultConfigValue, userConfigValue) -> defaultConfigValue);
                    });
                    return defaultStageConfig;
                });
            }
        });

        return defaultConfig;
    }

    public static ProgramConfig getDefaultUserConfig() {
        return ProgramConfig.builder()
                .stageConfig(new HashMap<>())
                .stageActivityConfig(new HashMap<>())
                .build();
    }
}
