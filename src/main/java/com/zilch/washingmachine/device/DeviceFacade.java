package com.zilch.washingmachine.device;

import java.time.Duration;

@SuppressWarnings("unused")
public interface DeviceFacade {
    void pourWater();

    void heatUp(double temperature);

    void spin(Duration duration);

    void idle(Duration duration);

    void pump();

    void fullStop();

}
