package com.zilch.washingmachine.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ProgramConfig {
    Map<StageType, Map<String, String>> stageConfig;
    Map<SubStageType, Map<String, String>> subStageConfig;
}
