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
    Map<StageType, Map<ConfigType, String>> stageConfig;
    Map<StageActivityType, Map<ConfigType, String>> subStageConfig;

//    public String getSubStageConfig(SubStageType subStageType, ConfigType configType) {
//
//    }
}
