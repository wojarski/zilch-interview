package com.zilch.washingmachine.endpoint;

import com.zilch.washingmachine.impl.LaundryService;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.Operation;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.program.AbstractProgram;
import com.zilch.washingmachine.program.ECOProgram;
import com.zilch.washingmachine.program.LaundryFactory;
import com.zilch.washingmachine.program.QuickProgram;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class LaundryEndpointImpl implements LaundryEndpoint {
    @Autowired
    private LaundryFactory programFactory;
    @Autowired
    private LaundryService laundryRunner;

    @Override
    public UUID runQuickLaundry() {
        QuickProgram program = QuickProgram.builder().build();

        // TODO user config comes from 'somewhere'
        ProgramConfig userConfig = ProgramConfig.builder().build();
        return runLaundry(program, userConfig);
    }

    @Override
    public UUID runECOLaundry() {
        ECOProgram program = ECOProgram.builder().build();

        // TODO user config comes from 'somewhere'
        ProgramConfig userConfig = ProgramConfig.builder().build();
        return runLaundry(program, userConfig);
    }

    private UUID runLaundry(AbstractProgram program, ProgramConfig userConfig) {
        Laundry laundry = programFactory.newLaundry(program, userConfig);
        Operation operation = laundryRunner.startNewLaundry(laundry);

        return operation.getLaundryId();
    }
}
