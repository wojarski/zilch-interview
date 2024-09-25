package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.model.Stage;
import com.zilch.washingmachine.persistence.model.StageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class StageMapper {
    public static final StageMapper INSTANCE = Mappers.getMapper(StageMapper.class);

    public abstract StageEntity mapToEntity(Stage stage);

    public abstract Stage mapFromEntity(StageEntity stageEntity);

}
