package com.zilch.washingmachine.device;

import java.time.Duration;

public interface DeviceFacade {
    void pourWater();

    boolean isWaterFull();

    void heatUp(double temperature);

    void spin(Duration duration);

    void idle(Duration duration);

    void pump();

    void fullStop();

}
