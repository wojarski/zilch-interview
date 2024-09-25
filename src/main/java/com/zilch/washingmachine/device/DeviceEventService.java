package com.zilch.washingmachine.device;

import com.zilch.washingmachine.impl.LaundryService;
import com.zilch.washingmachine.persistence.model.DeviceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceEventService {
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

}
