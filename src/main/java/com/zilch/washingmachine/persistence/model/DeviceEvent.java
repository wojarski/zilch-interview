package com.zilch.washingmachine.persistence.model;

import java.time.Clock;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class DeviceEvent extends ApplicationEvent {
    private UUID id;
    private String deviceSerialNumber;
    private DeviceEventType eventType;


    public DeviceEvent(UUID id, String deviceSerialNumber, DeviceEventType eventType) {
        super(deviceSerialNumber);
        this.id = id;
        this.deviceSerialNumber = deviceSerialNumber;
        this.eventType = eventType;
    }
    public DeviceEvent(Object source) {
        super(source);
    }

    public DeviceEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
