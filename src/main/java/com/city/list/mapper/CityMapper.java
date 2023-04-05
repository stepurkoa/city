package com.city.list.mapper;

import com.city.list.dto.CityDto;
import com.city.list.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDto fromEntity(City city);
}
