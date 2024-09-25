package com.zilch.washingmachine.device;

import com.zilch.washingmachine.persistence.model.DeviceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishDevcieEvent(DeviceEvent deviceEvent) {
        applicationEventPublisher.publishEvent(deviceEvent);
    }
}
