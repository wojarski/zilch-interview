package com.zilch.washingmachine.model;

import com.zilch.washingmachine.program.AbstractStage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RunningProgram {
    AbstractStage stage;
}
