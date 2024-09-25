package com.zilch.washingmachine.device;

import com.zilch.washingmachine.persistence.model.DeviceState;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class DummyDeviceConnector implements DeviceConnector{
    public static String DEFAULT_SERIAL_NUMBER = "CE0HS0E0300CP9BP0001";
    public static DeviceState DEFAULT_DEVICE_STATE = DeviceState.builder()
            .on(true)
            .empty(true)
            .errors(new ArrayList<>())
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .build();

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

    }

    @Override
    public void closeWaterValve() {

    }

    @Override
    public void heatingOn() {

    }

    @Override
    public void heatingOff() {

    }

    @Override
    public void engineOn() {

    }

    @Override
    public void engineOff() {

    }

    @Override
    public void pumpOn() {

    }

    @Override
    public void pumpOff() {

    }

    @Override
    public void abortAny() {

    }

    @Override
    public boolean isWaterFull() {
        return false;
    }
}
