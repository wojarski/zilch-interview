package com.zilch.washingmachine.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Stage {
    private UUID id;
    private UUID laundryId;
    private StageType type;
    private StageActivity activity;
    private Instant startedAt;
    private Instant finishedAt;
    private List<StageActivity> processedActivities;
}
