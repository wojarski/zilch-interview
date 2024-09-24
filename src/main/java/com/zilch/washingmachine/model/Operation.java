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
}
