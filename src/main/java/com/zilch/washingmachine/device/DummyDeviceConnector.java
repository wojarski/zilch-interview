package com.zilch.washingmachine.device;

import com.zilch.washingmachine.persistence.model.DeviceState;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyDeviceConnector implements DeviceConnector{
    public static String DEFAULT_SERIAL_NUMBER = "CE0HS0E0300CP9BP0001";
    public static DeviceState DEFAULT_DEVICE_STATE = DeviceState.builder()
            .on(true)
            .empty(true)
            .errors(new ArrayList<>())
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .build();

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    @Override
    public String getSerialNumber() {
        return DEFAULT_DEVICE_STATE.getSerialNumber();
    }

    @Override
    public DeviceState getDeviceState() {
        return DEFAULT_DEVICE_STATE;
    }

    @Override
    public void openWaterValve() {
        scheduler.schedule(() -> {
            DEFAULT_DEVICE_STATE.setWaterIn(true);
        }, 2, TimeUnit.SECONDS);

        log.info("### open valve");
    }

    @Override
    public void closeWaterValve() {
        log.info("### close valve");
    }

    @Override
    public void heatingOn() {
        log.info("### heating on");
    }

    @Override
    public void heatingOff() {
        log.info("### heating off");
    }

    @Override
    public void engineOn() {
        log.info("### engine on");
    }

    @Override
    public void engineOff() {
        log.info("### engine off");
    }

    @Override
    public void pumpOn() {
        log.info("### pump on");
    }

    @Override
    public void pumpOff() {
        log.info("### pump off");
    }

    @Override
    public void abortAll() {
        log.info("### abort all");
    }

    @Override
    public boolean isWaterFull() {
        return DEFAULT_DEVICE_STATE.isWaterIn();
    }
}
