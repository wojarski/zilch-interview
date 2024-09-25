package com.zilch.washingmachine.endpoint;

import com.zilch.washingmachine.model.Laundry;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public interface LaundryEndpoint {
    @GetMapping("/quickLaundry")
    // TODO check permissions
    // TODO query paremeters & args
    UUID runQuickLaundry();

    @GetMapping("/ECOLaundry")
    // TODO check permissions
    // TODO query paremeters & args
    UUID runECOLaundry();

    @GetMapping("/getAllLaundries")
    List<Laundry> getAllLaundries();
}
