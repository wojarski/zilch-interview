package com.zilch.washingmachine.program.stage;

import static com.zilch.washingmachine.model.ProgramConfig.ConfigType.REPEATS;
import static com.zilch.washingmachine.model.StageActivityType.IDLE;
import static com.zilch.washingmachine.model.StageActivityType.PUMP;
import static com.zilch.washingmachine.model.StageActivityType.SPIN;

import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.model.ProgramConfig.ConfigType;
import com.zilch.washingmachine.model.StageActivityType;
import com.zilch.washingmachine.model.StageType;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DrainStage extends AbstractStage {
    @Override
    public StageType getType() {
        return StageType.DRAIN;
    }

    @Override
    public List<StageActivityType> getActivities() {
        return List.of(SPIN, PUMP);
    }

    @Override
    public List<StageType> getAllowedPredecessors() {
        return List.of(StageType.SOAK, StageType.WASH);
    }

    @Override
    public List<StageActivityType> getEffectiveActivities(ProgramConfig config) {
        int repeats = Integer.parseInt(config.getValue(StageType.DRAIN, REPEATS));

        return IntStream.range(0, repeats).mapToObj(index -> List.of(SPIN, PUMP)).flatMap(List::stream).toList();
    }
}
