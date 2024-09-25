package com.zilch.washingmachine.impl;

import com.zilch.washingmachine.device.DeviceFacade;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.model.StageActivity;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.program.LaundryFactory;
import com.zilch.washingmachine.program.stage.AbstractStage;
import com.zilch.washingmachine.program.stage.DrainStage;
import com.zilch.washingmachine.program.stage.RinseStage;
import com.zilch.washingmachine.program.stage.SoakStage;
import com.zilch.washingmachine.program.stage.WashStage;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.zilch.washingmachine.model.StageActivityType.*;
import static com.zilch.washingmachine.model.ProgramConfig.ConfigType.*;

@Component
public class StageProcessor {
    @Autowired
    LaundryFactory laundryFactory;

    @Autowired
    Clock clock;

    @Autowired
    DeviceFacade deviceFacade;

    public void run(AbstractStage stage, ProgramConfig config) {
        Stage state = stage.getStage();
        if (state.getStartedAt() == null) {
            state.setStartedAt(clock.instant());
        }

        switch (state.getType()) {
            case SOAK -> handle((SoakStage) stage, config);
            case WASH -> handle((WashStage) stage, config);
            case DRAIN -> handle((DrainStage) stage, config);
            case RINSE -> handle((RinseStage) stage, config);
            default -> throw new IllegalArgumentException("unknown stage");
        }
    }

    // TODO improve utilizing config
    private void handle(StageActivity subStage, ProgramConfig config) {
        switch (subStage.getType()) {
            case POUR_WATER -> deviceFacade.pourWater();
            case HEAT_UP -> deviceFacade.heatUp(Double.parseDouble(config.getValue(HEAT_UP, TEMPERATURE)));
            case SPIN -> deviceFacade.spin(Duration.ofSeconds(Long.parseLong(config.getValue(SPIN, DURATION))));
            case IDLE -> deviceFacade.idle(Duration.ofSeconds(Long.parseLong(config.getValue(IDLE, DURATION))));
            case PUMP -> deviceFacade.pump();
            case FULL_STOP -> deviceFacade.fullStop();
            default -> throw new IllegalArgumentException("unknown sub stage");
        }
    }

    private void handle(SoakStage soakStage, ProgramConfig config) {
        Stage stage = soakStage.getStage();
        StageActivity activityToHandle = null;

        if (isSubStageTypeOf(stage, POUR_WATER)) {
            if (deviceFacade.isWaterFull()) {
                stage.getActivity().setFinishedAt(clock.instant());
                activityToHandle = findNextActivity(soakStage);
                stage.getProcessedActivities().add(stage.getActivity());
                stage.setActivity(activityToHandle);
            } else {
                activityToHandle = stage.getActivity();
            }
        } else if (isSubStageTypeOf(stage, IDLE)) {
            Duration duration = Duration.ofSeconds(Long.parseLong(config.getValue(IDLE, DURATION)));
            if (hasTimeElapsed(stage.getActivity(), duration)) {
                Instant finishedAt = clock.instant();
                stage.getActivity().setFinishedAt(finishedAt);
                stage.setFinishedAt(finishedAt);
            }
        }

        if (activityToHandle != null) {
            handle(activityToHandle, config);
        }
    }

    private void handle(WashStage stage, ProgramConfig config) {
        // TODO implement
    }

    public void handle(RinseStage stage, ProgramConfig config) {
        // TODO implement
    }

    private void handle(DrainStage stage, ProgramConfig config) {
        // TODO implement
    }

    private StageActivity findNextActivity(AbstractStage stage) {
        StageActivity lastActivity = stage.getStage().getActivity();

        Optional<StageActivityType> oNextActivityType = stage.getSubStages().stream()
                .dropWhile(Predicate.not(lastActivity.getType()::equals))
                .filter(Predicate.not(lastActivity.getType()::equals))
                .findFirst();

        return oNextActivityType
                .map(subStageType -> laundryFactory.newSubStage(subStageType, stage.getStage().getId()))
                .orElse(null);
    }

    private boolean isSubStateFinished(StageActivity subStage) {
        return subStage.getFinishedAt() != null;
    }

    private boolean isSubStageTypeOf(Stage stage, StageActivityType subStageType) {
         return subStageType.equals(stage.getActivity().getType());
    }

    private boolean hasTimeElapsed(StageActivity subStage, Duration duration) {
        long nowMili = clock.instant().toEpochMilli();
        return duration.compareTo(Duration.ofMillis(nowMili - subStage.getStartedAt().toEpochMilli())) < 0;
    }
}
