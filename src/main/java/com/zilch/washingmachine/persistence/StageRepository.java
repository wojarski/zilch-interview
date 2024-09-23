package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.persistence.model.LaundryEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface StageRepository extends CrudRepository<LaundryEntity, UUID> {

}
