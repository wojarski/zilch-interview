package com.zilch.washingmachine.impl;

import static org.mockito.ArgumentCaptor.forClass;

import com.zilch.washingmachine.LaundryTest;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.Operation;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.persistence.LaundryRepository;
import com.zilch.washingmachine.persistence.StageRepository;
import com.zilch.washingmachine.persistence.model.LaundryEntity;
import com.zilch.washingmachine.persistence.model.StageEntity;
import com.zilch.washingmachine.program.LaundryFactory;
import com.zilch.washingmachine.program.QuickProgram;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@LaundryTest
public class LaundryServiceTest {
    @Autowired
    private LaundryService laundryService;
    @Autowired
    private LaundryFactory laundryFactory;

    @SpyBean
    private LaundryRepository laundryRepository;
    @SpyBean
    private StageRepository stageRepository;
    @SpyBean
    private StageProcessor stageProcessor;

    @Test
    void shouldStartNewLaundry() {
        // Arrange
        ProgramConfig userConfig = LaundryFactory.getDefaultUserConfig();
        Laundry laundry = laundryFactory.newLaundry(QuickProgram.builder().build(), userConfig);

        // Act
        laundryService.startNewLaundry(laundry);

        // Assert
        ArgumentCaptor<LaundryEntity> laundryEntityArgumentCaptor = forClass(LaundryEntity.class);
        Mockito.verify(laundryRepository, Mockito.times(1)).save(laundryEntityArgumentCaptor.capture());
        LaundryEntity laundryEntity = laundryEntityArgumentCaptor.getValue();
        assertNotNull(laundryEntity.getId());

        ArgumentCaptor<StageEntity> stageEntityArgumentCaptor = forClass(StageEntity.class);
        Mockito.verify(stageRepository, Mockito.times(1)).save(stageEntityArgumentCaptor.capture());
        StageEntity stageEntity = stageEntityArgumentCaptor.getValue();
        assertNotNull(stageEntity.getId());

        Mockito.verify(stageProcessor).run(any(), any());
    }

}
