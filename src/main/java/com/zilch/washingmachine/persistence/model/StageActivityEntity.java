package com.zilch.washingmachine.persistence.model;

import com.zilch.washingmachine.model.StageActivityType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
public class StageActivityEntity {
    @Id
    private UUID id;
    private UUID stageId;
    private StageActivityType type;
    private Instant startedAt;
    private Instant finishedAt;
}
