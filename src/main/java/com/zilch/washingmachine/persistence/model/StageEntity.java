package com.zilch.washingmachine.persistence.model;

import com.zilch.washingmachine.model.StageType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StageEntity {
    @Id
    private UUID id;
    private UUID laundryId;
    private StageType type;
    @OneToOne
    private StageActivityEntity activity;
    private Instant startedAt;
    private Instant finishedAt;
    @OneToMany
    private List<StageActivityEntity> processedActivities;
}
