package com.zilch.washingmachine.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zilch.washingmachine.LaundryTest;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageType;
import com.zilch.washingmachine.program.stage.AbstractStage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@LaundryTest
public class LaundryFactoryTest {
    @Autowired
    private LaundryFactory laundryFactory;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateNewLaundry() throws JsonProcessingException {
        // Arrange
        ProgramConfig userConfig = LaundryFactory.getDefaultUserConfig();
        AbstractProgram program = ECOProgram.builder().build();

        // Act
        Laundry laundry = laundryFactory.newLaundry(program, userConfig);

        // Assert
        assertNotNull(laundry.getId());
        assertNotNull(laundry.getProgram());
        AbstractStage abstractStage = laundry.getStage();
        assertEquals(StageType.SOAK, abstractStage.getType());
        assertNotNull(abstractStage.getStage().getId());
        assertEquals(laundry.getId(), abstractStage.getStage().getLaundryId());
        ProgramConfig programDefaultConfig = program.getProgramDefaultConfig();
        assertEquals(objectMapper.writeValueAsString(programDefaultConfig), objectMapper.writeValueAsString(laundry.getProgram().getProgramConfig()));
    }

    @Test
    void shouldConfigureProperly() throws JsonProcessingException {
        // Arrange
        ProgramConfig defaultConfig = LaundryFactory.getDefaultUserConfig();
        Map<StageType, Map<ConfigType, String>> stageConfig = new HashMap<>(defaultConfig.getStageConfig());
        stageConfig.put(StageType.WASH, new HashMap<>(Map.of(ConfigType.TEMPERATURE, "31")));

        ProgramConfig userConfig = defaultConfig.toBuilder().stageConfig(stageConfig).build();
        AbstractProgram program = ECOProgram.builder().build();

        // Act
        Laundry laundry = laundryFactory.newLaundry(program, userConfig);

        // Assert
        ProgramConfig modifiedConfig = laundry.getProgram().getProgramConfig();
        assertEquals("31", modifiedConfig.getValue(StageType.WASH, ConfigType.TEMPERATURE));
    }
}
