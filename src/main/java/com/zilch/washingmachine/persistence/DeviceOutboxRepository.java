package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.persistence.model.DeviceOutboxEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceOutboxRepository extends JpaRepository<DeviceOutboxEntity, UUID> {
    List<DeviceOutboxEntity> findByLaundryId(UUID laundryId);
    List<DeviceOutboxEntity> findByCompleted(boolean completed);
}
