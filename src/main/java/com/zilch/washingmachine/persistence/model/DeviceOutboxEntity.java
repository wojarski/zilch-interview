package com.zilch.washingmachine.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@EntityListeners(DeviceOutboxEntityListener.class)
public class DeviceOutboxEntity {
    @Id
    private UUID id;
    private UUID laundryId;
    private String serialNumber;
    private DeviceOutboxAction action;
    private boolean completed;
}
