package com.zilch.washingmachine.endpoint;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.UUID;

@Path("laundry")
public interface LaundryEndpoint {
    @POST
    @Path("/quickLaundry")
    // TODO check permissions
    // TODO query paremeters & args
    UUID runQuickLaundry();

    @POST
    @Path("/ECOLaundry")
    // TODO check permissions
    // TODO query paremeters & args
    UUID runECOLaundry();
}
