package com.zilch.washingmachine.device;

import com.zilch.washingmachine.impl.LaundryService;
import com.zilch.washingmachine.persistence.model.DeviceEvent;
import com.zilch.washingmachine.persistence.model.DeviceEventType;
import com.zilch.washingmachine.persistence.model.DeviceState;
import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceFacadeImpl implements DeviceFacade {
    @Autowired
    Clock clock;

    @Autowired
    DeviceConnector deviceConnector;

    @Autowired
    DeviceEventPublisher deviceEventPublisher;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

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
        deviceConnector.abortAny();
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

    @Override
    public boolean isWaterFull() {
        return deviceConnector.isWaterFull();
    }

    private void stopSpin() {
        deviceConnector.engineOff();
    }

    private void idleElapsed(String serialNumber) {
        deviceEventPublisher.publishDevcieEvent(DeviceEvent.builder()
                                                        .deviceSerialNumber(serialNumber)
                                                        .eventType(DeviceEventType.IDLE_ELAPSED)
                                                        .build());
    }

}
