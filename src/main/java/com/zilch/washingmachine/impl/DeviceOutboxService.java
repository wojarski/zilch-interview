package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.device.DeviceFacade;
import com.zilch.washingmachine.device.DummyDeviceConnector;
import com.zilch.washingmachine.persistence.DeviceOutboxRepository;
import com.zilch.washingmachine.persistence.model.DeviceOutboxAction;
import com.zilch.washingmachine.persistence.model.DeviceOutboxEntity;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class DeviceOutboxService {
    @Autowired
    DeviceOutboxRepository deviceOutboxRepository;

    @Autowired
    DeviceFacade deviceFacade;

    public void pourWater() {
        deviceOutboxRepository.save(buildOutboxEntity(DeviceOutboxAction.POUR_WATER));
    }

    public void heatUp(double v) {
        deviceOutboxRepository.save(buildOutboxEntity(DeviceOutboxAction.HEAT_UP));
    }

    public void spin(Duration duration) {
        deviceOutboxRepository.save(buildOutboxEntity(DeviceOutboxAction.SPIN));
    }

    public void idle(Duration duration) {
        deviceOutboxRepository.save(buildOutboxEntity(DeviceOutboxAction.IDLE));
    }

    public void pump() {
        deviceOutboxRepository.save(buildOutboxEntity(DeviceOutboxAction.PUMP));
    }

    public void fullStop() {
        deviceOutboxRepository.save(buildOutboxEntity(DeviceOutboxAction.FULL_STOP));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void performActions() {
        List<DeviceOutboxEntity> actions = deviceOutboxRepository.findByCompleted(false);

        // TODO implement retry etc.
        actions.forEach(deviceOutboxEntity -> {
            try {
                deviceFacade.pourWater();
                deviceOutboxEntity.setCompleted(true);
                deviceOutboxRepository.save(deviceOutboxEntity);
            } catch (Exception e) {
                log.error("Could not perform action {} try again later", deviceOutboxEntity.getAction());
            }
        });
    }

    private DeviceOutboxEntity buildOutboxEntity(DeviceOutboxAction deviceOutboxAction) {
        return DeviceOutboxEntity.builder()
                .id(UUID.randomUUID())
                .action(deviceOutboxAction)
                .serialNumber(DummyDeviceConnector.DEFAULT_SERIAL_NUMBER)
                .build();
    }
}
