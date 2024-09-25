package com.zilch.washingmachine.program;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.program.stage.AbstractStage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractProgram {
    protected ProgramConfig programConfig;
    public abstract List<AbstractStage> getStages();
    public abstract ProgramConfig getProgramDefaultConfig();
    public abstract ProgramConfig getProgramConfig();
}
