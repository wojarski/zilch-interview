package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.model.DeviceEvent;
import com.zilch.washingmachine.model.ProgramEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishDeviceEvent(DeviceEvent deviceEvent) {
        applicationEventPublisher.publishEvent(deviceEvent);
    }

    public void publishProgramEvent(ProgramEvent programEvent) {
        applicationEventPublisher.publishEvent(programEvent);
    }
}
