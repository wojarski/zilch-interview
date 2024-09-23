package com.zilch.washingmachine.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Laundry {
    private AbstractStage stage;
    private boolean canPause;

//    public abstract void run();
//    public abstract void pause();
//    public abstract void resume();
}
