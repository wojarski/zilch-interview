package com.zilch.washingmachine.persistence.model;

import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceOutboxEntityListener {

    @PostPersist
    void postPersist(DeviceOutboxEntity entity) {
        log.info("new outbox action {} saved", entity.getAction());
    }
}
