package com.zilch.washingmachine.model;

import com.zilch.washingmachine.DeviceFacade;
import com.zilch.washingmachine.model.AbstractStage;
import com.zilch.washingmachine.model.StageType;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
@Builder
@Slf4j
public class SoakStage extends AbstractStage {
    @Autowired
    DeviceFacade deviceFacade;

    private Duration duration;

    @Override
    public StageType getType() {
        return StageType.SOAK;
    }

    @Override
    public List<SubStageType> getSubStages() {
        return List.of(SubStageType.POUR_WATER, SubStageType.IDLE);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of();
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public void run() {
        log.info("Puring water");
    }
}
