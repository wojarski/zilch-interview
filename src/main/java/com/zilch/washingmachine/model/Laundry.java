package com.zilch.washingmachine.model;

import com.zilch.washingmachine.program.AbstractProgram;
import com.zilch.washingmachine.program.AbstractStage;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Laundry {
    private UUID id;
    private String deviceSerialNumber;
    private AbstractProgram program;
    private AbstractStage stage;
    private List<Stage> processedStages;
}
