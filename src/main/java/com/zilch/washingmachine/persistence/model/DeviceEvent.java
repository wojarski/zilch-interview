package com.zilch.washingmachine.persistence.model;

import java.time.Clock;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class DeviceEvent extends ApplicationEvent {
    UUID id;
    String deviceSerialNumber;
    DeviceEventType eventType;

    public DeviceEvent(Object source) {
        super(source);
    }

    public DeviceEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
