package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.persistence.model.LaundryEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaundryRepository extends JpaRepository<LaundryEntity, UUID> {
    Optional<LaundryEntity> findByDeviceSerialNumber(String serialNumber);
}
