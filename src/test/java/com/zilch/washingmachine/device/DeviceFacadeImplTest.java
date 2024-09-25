package com.zilch.washingmachine.device;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zilch.washingmachine.LaundryTest;
import com.zilch.washingmachine.impl.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

@LaundryTest
public class DeviceFacadeImplTest {
    @Autowired
    DeviceFacade deviceFacade;

    @SpyBean
    DeviceConnector deviceConnectorSpy;

    @SpyBean
    EventPublisher eventPublisherSpy;

    @Test
    void shouldPourWater() {
        // Act
        deviceFacade.pourWater();

        // Assert
        verify(deviceConnectorSpy, times(1)).openWaterValve();
    }

    @Test
    void shouldIdle() {
        // TODO
    }
}
