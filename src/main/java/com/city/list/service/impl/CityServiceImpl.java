package com.city.list.service.impl;

import com.city.list.mapper.CityMapper;
import com.city.list.service.CityService;
import com.city.list.dto.CityDto;
import com.city.list.dto.CustomPage;
import com.city.list.entity.City;
import com.city.list.exceptions.ServiceError;
import com.city.list.exceptions.ServiceException;
import com.city.list.repository.CityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper mapper;

    @Override
    public CustomPage<CityDto> getCities(Pageable page) {
        return new CustomPage<>(
                cityRepository.findAll(page)
                        .map(mapper::fromEntity));
    }

    @Override
    public CustomPage<CityDto> searchCitiesByName(String name, Pageable page) {
        return new CustomPage<>(
                cityRepository.searchCityByName(name, page)
                        .map(mapper::fromEntity));
    }

    @Override
    @Transactional
    public CityDto editCity(CityDto cityDto) {
        City city = cityRepository.findById(cityDto.getId())
                .orElseThrow(() -> ServiceException.builder(ServiceError.CITY_NOT_FOUND_EXCEPTION).build());
        return mapper.fromEntity(
                cityRepository.save(city
                        .setName(cityDto.getName())
                        .setPhoto(cityDto.getPhoto())));
    }
}
