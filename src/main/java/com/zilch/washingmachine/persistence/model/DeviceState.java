package com.zilch.washingmachine.persistence.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceState {
    private boolean on;
    private boolean error;
    private boolean spinning;
    private boolean pumpOn;
    private boolean balanced;
    private boolean waterIn;
    private boolean powderIn;
    private double waterTemperature;
    private boolean empty;
    private List<DeviceError> errors;
    private String serialNumber;
}
