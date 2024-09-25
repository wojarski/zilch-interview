package com.zilch.washingmachine.model;

import java.time.Clock;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class ProgramEvent extends ApplicationEvent {
    private ProgramEventType programEventType;
    private StageType stageType;
    private UUID laundryId;

    public ProgramEvent(ProgramEventType programEventType, StageType stageType, UUID laundryId) {
        super(laundryId);
        this.programEventType = programEventType;
        this.stageType = stageType;
        this.laundryId = laundryId;
    }

    public ProgramEvent(Object source) {
        super(source);
    }

    public ProgramEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
