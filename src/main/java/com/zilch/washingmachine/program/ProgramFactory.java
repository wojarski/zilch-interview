package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageType;
import java.util.Map;
import java.util.UUID;

public class ProgramFactory {

    public Laundry newLaundry(AbstractProgram program, ProgramConfig userConfig) {
        AbstractStage stage = program.getStages().getFirst();

        ProgramConfig defaultConfig = program.getProgramDefaultConfig();
        ProgramConfig config = configure(defaultConfig, userConfig);
        program.setProgramConfig(config);

        Laundry laundry = Laundry.builder()
                .id(UUID.randomUUID())
                .stage(stage)
                .program(program)
                .build();

        return laundry;
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
