package com.zilch.washingmachine.impl;

import static com.zilch.washingmachine.model.ProgramConfig.ConfigType.DURATION;
import static com.zilch.washingmachine.model.ProgramConfig.ConfigType.TEMPERATURE;
import static com.zilch.washingmachine.model.StageActivityType.HEAT_UP;
import static com.zilch.washingmachine.model.StageActivityType.IDLE;
import static com.zilch.washingmachine.model.StageActivityType.POUR_WATER;
import static com.zilch.washingmachine.model.StageActivityType.SPIN;

import com.zilch.washingmachine.device.DummyDeviceConnector;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramEvent;
import com.zilch.washingmachine.model.ProgramEventType;
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
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StageProcessor {
    @Autowired
    LaundryFactory laundryFactory;

    @Autowired
    Clock clock;

    @Autowired
    DeviceOutboxService deviceOutboxService;

    @Autowired
    EventPublisher eventPublisher;

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
            case POUR_WATER -> deviceOutboxService.pourWater();
            case HEAT_UP -> deviceOutboxService.heatUp(Double.parseDouble(config.getValue(HEAT_UP, TEMPERATURE)));
            case SPIN -> deviceOutboxService.spin(Duration.ofSeconds(Long.parseLong(config.getValue(SPIN, DURATION))));
            case IDLE -> deviceOutboxService.idle(Duration.ofSeconds(Long.parseLong(config.getValue(IDLE, DURATION))));
            case PUMP -> deviceOutboxService.pump();
            case FULL_STOP -> deviceOutboxService.fullStop();
            default -> throw new IllegalArgumentException("unknown sub stage");
        }
    }

    private void handle(SoakStage soakStage, ProgramConfig config) {
        Stage stage = soakStage.getStage();
        StageActivity waterActivity = checkWater(soakStage, config);

        if (waterActivity != null) {
            handle(waterActivity, config);
        } else {
            StageActivity idleActivity = findActivity(soakStage, config);
            if (idleActivity != null) {
                stage.setActivity(idleActivity);
                handle(idleActivity, config);
            } else {
                log.info("soak stage finished");
                publishStageFinishedEvent(stage);
            }
        }
    }

    private void handle(WashStage washStage, ProgramConfig config) {
        Stage stage = washStage.getStage();
        StageActivity waterActivity = checkWater(washStage, config);

        if (waterActivity != null) {
            handle(waterActivity, config);
        } else {
            StageActivity spinIdleActivity = findActivity(washStage, config);
            if (spinIdleActivity != null) {
                stage.setActivity(spinIdleActivity);
                handle(spinIdleActivity, config);
            } else {
                log.info("wash stage finished");
                publishStageFinishedEvent(stage);
            }
        }

    }

    public void handle(RinseStage stage, ProgramConfig config) {
        // TODO implement
    }

    private void handle(DrainStage stage, ProgramConfig config) {
        // TODO implement
    }

    private void publishStageFinishedEvent(Stage stage) {
        eventPublisher.publishProgramEvent(ProgramEvent.builder()
                                                   .programEventType(ProgramEventType.STAGE_FINISHED)
                                                   .stageType(stage.getType())
                                                   .laundryId(stage.getLaundryId())
                                                   .build());
    }

    private StageActivity checkWater(AbstractStage abstractStage, ProgramConfig config) {
        Stage stage = abstractStage.getStage();
        StageActivity activity = stage.getActivity();

        if (isActivity(stage, POUR_WATER)) {
            if (isWaterIn()) {
                activity.setFinishedAt(clock.instant());
                stage.getProcessedActivities().add(activity);
            } else {
                return activity;
            }
        }
        return null;
    }

    private StageActivity tryActivity(WashStage washStage, StageActivityType activityType, ProgramConfig config) {
        StageActivity activityToHandle = null;
        Stage stage = washStage.getStage();

        if (isActivity(stage, activityType)) {
            if (isWaterIn()) {
                stage.getActivity().setFinishedAt(clock.instant());
                stage.getProcessedActivities().add(stage.getActivity());
                activityToHandle = findActivity(washStage, config);
                stage.setActivity(activityToHandle);
            } else {
                activityToHandle = stage.getActivity();
            }
        }
        return activityToHandle;
    }


    private StageActivity findActivity(AbstractStage stage, ProgramConfig config) {
        List<StageActivityType> processedActivitiesTypes = new ArrayList<>(stage.getStage().getProcessedActivities()).stream()
                .map(StageActivity::getType)
                .toList();

        List<StageActivityType> allActivities = new ArrayList<>(stage.getEffectiveActivities(config));
        allActivities.removeAll(processedActivitiesTypes);

        if (!allActivities.isEmpty()) {
            return laundryFactory.newStageActivity(allActivities.getFirst(), stage.getStage().getId());
        } else {
            return null;
        }
    }

    private boolean isSubStateFinished(StageActivity subStage) {
        return subStage.getFinishedAt() != null;
    }

    private boolean isActivity(Stage stage, StageActivityType subStageType) {
         return subStageType.equals(stage.getActivity().getType());
    }

    private boolean hasTimeElapsed(StageActivity subStage, Duration duration) {
        long nowMili = clock.instant().toEpochMilli();
        return duration.compareTo(Duration.ofMillis(nowMili - subStage.getStartedAt().toEpochMilli())) < 0;
    }

    private boolean isWaterIn() {
        return DummyDeviceConnector.DEFAULT_DEVICE_STATE.isWaterIn();
    }
}
