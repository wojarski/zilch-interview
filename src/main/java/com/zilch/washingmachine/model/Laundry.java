package com.zilch.washingmachine.model;

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
    private AbstractStage stage;
    private boolean canPause;

//    public abstract void run();
//    public abstract void pause();
//    public abstract void resume();
}
