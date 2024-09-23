package com.zilch.washingmachine.persistence;

import com.zilch.washingmachine.model.Laundry;
import com.zilch.washingmachine.persistence.model.LaundryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class LaundryMapper {
    public static final LaundryMapper INSTANCE = Mappers.getMapper(LaundryMapper.class);

    public abstract LaundryEntity mapToEntity(Laundry laundry);

    public abstract Laundry mapFromEntity(LaundryEntity laundryEntity);

}
