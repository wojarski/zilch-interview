package com.zilch.washingmachine.program.stage;

import static com.zilch.washingmachine.model.ProgramConfig.ConfigType.REPEATS;
import static com.zilch.washingmachine.model.StageActivityType.IDLE;
import static com.zilch.washingmachine.model.StageActivityType.SPIN;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.model.StageType;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
@AllArgsConstructor
public class WashStage extends AbstractStage {
    @Override
    public StageType getType() {
        return StageType.WASH;
    }

    @Override
    public List<StageActivityType> getActivities() {
        return List.of(StageActivityType.POUR_WATER, StageActivityType.SPIN, StageActivityType.IDLE);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of(StageType.SOAK, StageType.RINSE);
    }

    public List<StageActivityType> getEffectiveActivities(ProgramConfig config) {
        int spinRepeats = Integer.parseInt(config.getValue(SPIN, REPEATS));
        int idleRepeats = Integer.parseInt(config.getValue(IDLE, REPEATS));
        int sharedRepeats = Math.min(spinRepeats, idleRepeats);
        int nonSharedRepeats = Math.max(spinRepeats, idleRepeats) - sharedRepeats;

        return Stream.concat(
            Stream.concat(
                Stream.of(StageActivityType.POUR_WATER),
                IntStream.range(0, sharedRepeats).mapToObj(index -> List.of(SPIN, IDLE)).flatMap(List::stream)),
            IntStream.range(0, nonSharedRepeats).mapToObj(index -> spinRepeats > idleRepeats ? SPIN : IDLE)
        ).toList();
    }
}
