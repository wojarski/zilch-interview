package com.zilch.washingmachine.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceError {
    private DeviceErrorType errorType;
    private DeviceErrorSeverity severity;
    private String description;
    private Troubleshooting troubleshooting;
}
