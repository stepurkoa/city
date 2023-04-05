package com.city.list.service;

import com.city.list.dto.CityDto;
import com.city.list.dto.CustomPage;
import org.springframework.data.domain.Pageable;

public interface CityService {
    CustomPage<CityDto> getCities(Pageable page);

    CustomPage<CityDto> searchCitiesByName(String name, Pageable page);

    CityDto editCity(CityDto cityDto);
}
