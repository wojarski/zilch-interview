package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.persistence.model.StageEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface StageRepository extends CrudRepository<StageEntity, UUID> {

}
