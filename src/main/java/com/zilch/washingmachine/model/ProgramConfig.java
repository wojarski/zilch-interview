package com.zilch.washingmachine.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProgramConfig {
    public enum ConfigType {
        DURATION, REPEATS, TEMPERATURE
    }
    private Map<StageType, Map<ConfigType, String>> stageConfig;
    private Map<StageActivityType, Map<ConfigType, String>> stageActivityConfig;

    public String getValue(StageActivityType stageActivityType, ConfigType configType) {
        return stageActivityConfig.get(stageActivityType).get(configType);
    }

    public String getValue(StageType stageType, ConfigType configType) {
        return stageConfig.get(stageType).get(configType);
    }
}
