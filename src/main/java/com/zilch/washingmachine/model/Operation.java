package com.zilch.washingmachine.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Operation {
    private OperationStatus operationStatus;
    private UUID laundryId;
    String message;

    public Operation(OperationStatus operationStatus, UUID id) {
        this(operationStatus, id, "");
    }
}



