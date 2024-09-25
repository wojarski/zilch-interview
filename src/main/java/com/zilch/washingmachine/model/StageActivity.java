package com.zilch.washingmachine.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StageActivity {
    @Setter(AccessLevel.NONE)
    private UUID stageId;
    @Setter(AccessLevel.NONE)
    private UUID id;
    private StageActivityType type;
    private Instant startedAt;
    private Instant finishedAt;
}
