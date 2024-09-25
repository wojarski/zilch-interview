package com.zilch.washingmachine.device;

import com.zilch.washingmachine.persistence.model.DeviceState;

public interface DeviceConnector {
    String getSerialNumber();
    DeviceState getDeviceState();
    void openWaterValve();
    void closeWaterValve();
    void heatingOn();
    void heatingOff();
    void engineOn();
    void engineOff();
    void pumpOn();
    void pumpOff();
    void abortAll();
    boolean isWaterFull();
}
