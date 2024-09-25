package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.device.DeviceConnector;
import com.zilch.washingmachine.model.DeviceEvent;
import com.zilch.washingmachine.model.ProgramEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventService {
    @Autowired
    private DeviceConnector deviceConnector;
    @Autowired
    private LaundryService laundryService;

    @EventListener(DeviceEvent.class)
    public void onDeviceEvent(DeviceEvent deviceEvent) {
        switch(deviceEvent.getEventType()) {
            case WATER_FULL -> {
                deviceConnector.closeWaterValve();
                laundryService.continueLaundry(deviceEvent.getDeviceSerialNumber());
            }
            case WATER_TEMPERATURE -> deviceConnector.heatingOff();
            case NO_WATER -> deviceConnector.pumpOff();
            case IDLE_ELAPSED -> laundryService.continueLaundry(deviceEvent.getDeviceSerialNumber());
            default -> log.warn("unsupported device event type {}", deviceEvent.getEventType());
        }
    }

    @EventListener(ProgramEvent.class)
    public void onProgramEvent(ProgramEvent programEvent) {
        switch (programEvent.getProgramEventType()) {
            case STAGE_FINISHED -> {
                log.info("Stage finished. Continue with next stage");
                laundryService.continueLaundry(programEvent.getLaundryId());
            }
            default -> log.warn("unrecognized program event");
        }
    }

}
