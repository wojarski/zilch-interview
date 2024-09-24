package com.zilch.washingmachine.model;

import com.zilch.washingmachine.program.AbstractStage;
import com.zilch.washingmachine.model.stage.Stage;
import com.zilch.washingmachine.program.AbstractProgram;
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
    private AbstractProgram program;
    private AbstractStage stage;
    private List<Stage> processedStages;
    private boolean canPause;

//    public abstract void run();
//    public abstract void pause();
//    public abstract void resume();
}
