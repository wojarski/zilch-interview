package com.zilch.washingmachine.persistence.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    private int waterTemperature;
    private boolean empty;
    private List<DeviceError> errors;
}
