package com.zilch.washingmachine.persistence.model;

import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.program.AbstractProgram;
import com.zilch.washingmachine.program.ProgramType;
import com.zilch.washingmachine.program.stage.AbstractStage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
public class LaundryEntity {
    @Id
    private UUID id;
    private String deviceSerialNumber;
    private ProgramType programType;
    @JoinColumn(name = "stage")
    @OneToOne(fetch = FetchType.EAGER)
    private StageEntity stage;
}
