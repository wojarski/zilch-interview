package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.persistence.model.LaundryEntity;
import org.aspectj.lang.annotation.After;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = StageMapper.class)
public abstract class LaundryMapper {
    public static final LaundryMapper INSTANCE = Mappers.getMapper(LaundryMapper.class);

    @Mapping(target = "stage", ignore = true)
    public abstract LaundryEntity mapToEntity(Laundry laundry);

//    @AfterMapping
//    LaundryEntity onAfterMapping(Laundry laundry, @MappingTarget LaundryEntity laundryEntity) {
//        laundry.getStage();
//        return laundryEntity;
//    }

    @Mapping(target = "stage", ignore = true)
    public abstract Laundry mapFromEntity(LaundryEntity laundryEntity);

}
