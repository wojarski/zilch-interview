package com.zilch.washingmachine.endpoint;

import com.zilch.washingmachine.impl.LaundryService;
import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.model.Operation;
import com.zilch.washingmachine.model.ProgramConfig;
import com.zilch.washingmachine.program.AbstractProgram;
import com.zilch.washingmachine.program.ECOProgram;
import com.zilch.washingmachine.program.LaundryFactory;
import com.zilch.washingmachine.program.QuickProgram;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class LaundryEndpointImpl implements LaundryEndpoint {
    @Autowired
    private LaundryFactory laundryFactory;
    @Autowired
    private LaundryService laundryService;

    @Override
    public UUID runQuickLaundry() {
        QuickProgram program = QuickProgram.builder().build();

        ProgramConfig userConfig = LaundryFactory.getDefaultUserConfig();
        return runLaundry(program, userConfig);
    }

    @Override
    public UUID runECOLaundry() {
        ECOProgram program = ECOProgram.builder().build();

        ProgramConfig userConfig = LaundryFactory.getDefaultUserConfig();
        return runLaundry(program, userConfig);
    }

    @Override
    public List<Laundry> getAllLaundries() {
        return laundryService.listAllLaundries();
    }

    private UUID runLaundry(AbstractProgram program, ProgramConfig userConfig) {
        Laundry laundry = laundryFactory.newLaundry(program, userConfig);
        Operation operation = laundryService.startNewLaundry(laundry);

        return operation.getLaundryId();
    }
}
