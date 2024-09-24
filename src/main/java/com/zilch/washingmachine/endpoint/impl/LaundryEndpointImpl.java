package com.zilch.washingmachine.endpoint.impl;

import com.zilch.washingmachine.endpoint.LaundryEndpoint;
import com.zilch.washingmachine.impl.LaundryRunner;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.Operation;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.program.ProgramFactory;
import com.zilch.washingmachine.program.QuickProgram;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class LaundryEndpointImpl implements LaundryEndpoint {
    @Autowired
    private ProgramFactory programFactory;
    @Autowired
    private LaundryRunner laundryRunner;

    @Override
    public UUID runQuickLaundry() {
        QuickProgram program = QuickProgram.builder().build();

        // TODO user config comes from 'somewhere'
        ProgramConfig userConfig = ProgramConfig.builder().build();

        Laundry laundry = programFactory.newLaundry(program, userConfig);
        Operation operation = laundryRunner.run(laundry);

        return operation.getLaundryId();
    }
}
