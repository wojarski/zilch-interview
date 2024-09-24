package com.zilch.washingmachine.model.stage;

import com.zilch.washingmachine.model.StageType;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Stage {
    private StageType stageType;
    private Instant startedAt;
    private Instant finishedAt;
}
