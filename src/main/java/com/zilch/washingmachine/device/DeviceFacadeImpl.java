package com.zilch.washingmachine.device;

import com.zilch.washingmachine.impl.EventPublisher;
import com.zilch.washingmachine.model.DeviceEvent;
import com.zilch.washingmachine.model.DeviceEventType;
import com.zilch.washingmachine.persistence.model.DeviceState;
import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceFacadeImpl implements DeviceFacade {
    @Autowired
    private DeviceConnector deviceConnector;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private ScheduledExecutorService scheduler;

    @Override
    public void pourWater() {
        DeviceState deviceState = deviceConnector.getDeviceState();

        if (!deviceState.isWaterIn()) {
            deviceConnector.openWaterValve();
        }
    }

    @Override
    public void heatUp(double temperature) {
        DeviceState deviceState = deviceConnector.getDeviceState();
        if (deviceState.getWaterTemperature() < temperature) {
            deviceConnector.heatingOn();
        }
    }

    @Override
    public void spin(Duration duration) {
        deviceConnector.engineOn();

        scheduler.schedule(this::stopSpin, duration.toSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void idle(Duration duration) {
        deviceConnector.abortAll();
        String serialNumber = deviceConnector.getSerialNumber();

        scheduler.schedule(() -> idleElapsed(serialNumber), duration.toSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void pump() {
        deviceConnector.pumpOn();
    }

    @Override
    public void fullStop() {
    }

    private void stopSpin() {
        deviceConnector.engineOff();
    }

    // TODO I don't like it here
    private void idleElapsed(String serialNumber) {
        eventPublisher.publishDeviceEvent(DeviceEvent.builder()
                                                        .deviceSerialNumber(serialNumber)
                                                        .eventType(DeviceEventType.IDLE_ELAPSED)
                                                        .build());
    }

}
