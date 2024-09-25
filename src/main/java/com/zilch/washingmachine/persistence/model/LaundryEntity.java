package com.zilch.washingmachine.persistence.model;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.program.AbstractProgram;
import com.zilch.washingmachine.program.AbstractStage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class LaundryEntity {
    private UUID id;
    private String deviceSerialNumber;
    private AbstractProgram program;
    private AbstractStage stage;
}
