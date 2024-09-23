package com.zilch.washingmachine.predefined;

import com.zilch.washingmachine.model.AbstractStage;
import java.util.List;

public interface AbstractProgram {
    List<AbstractStage> getStages();
}
