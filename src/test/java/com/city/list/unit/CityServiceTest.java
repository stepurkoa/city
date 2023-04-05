package com.city.list.unit;

import com.city.list.dto.CustomPage;
import com.city.list.exceptions.ServiceException;
import com.city.list.mapper.CityMapper;
import com.city.list.mapper.CityMapperImpl;
import com.city.list.repository.CityRepository;
import com.city.list.service.CityService;
import com.city.list.service.impl.CityServiceImpl;
import com.city.list.dto.CityDto;
import com.city.list.entity.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CityServiceTest {

    private final CityRepository cityRepository = mock(CityRepository.class);
    private CityService cityService;
    private CityMapper mapper;

    @BeforeEach
    void init() {
        mapper = new CityMapperImpl();
        cityService =
                new CityServiceImpl(cityRepository, mapper);
    }

    @Test
    void should_returnPageWithCityDto() {
        List<City> cityList = List.of(new City()
                .setId(1L)
                .setName("name")
                .setPhoto("photo")
                .setVersion(0));

        when(cityRepository.findAll((Pageable) any()))
                .thenReturn(new PageImpl<>(cityList, PageRequest.of(0, 1), 1));


        CustomPage<CityDto> cities = cityService.getCities(PageRequest.of(0, 1));
        verify(cityRepository).findAll((Pageable) any());
        verify(cityRepository, atLeastOnce()).findAll((Pageable) any());

        Assertions.assertEquals("name", cities.getContent().stream().findFirst().get().getName());
        Assertions.assertEquals("photo", cities.getContent().stream().findFirst().get().getPhoto());
    }

    @Test
    void should_returnPageWithSearchCity() {
        List<City> cityList = List.of(
                new City().setId(1L).setName("name1").setPhoto("photo1").setVersion(0),
                new City().setId(2L).setName("name2").setPhoto("photo2").setVersion(0),
                new City().setId(3L).setName("name3").setPhoto("photo3").setVersion(0));


        when(cityRepository.searchCityByName(anyString(), any()))
                .thenReturn(new PageImpl<>(cityList, PageRequest.of(0, 3), 3));


        CustomPage<CityDto> cities = cityService.searchCitiesByName("text", PageRequest.of(0, 1));
        verify(cityRepository).searchCityByName(anyString(), any());
        verify(cityRepository, atLeastOnce()).searchCityByName(anyString(), any());

        Assertions.assertEquals(3, cities.getContent().size());
    }

    @Test
    void should_editCityEntity() {
        City city = new City().setId(1L).setName("name1").setPhoto("http://www.photo.com").setVersion(0);

        when(cityRepository.findById(any())).thenReturn(Optional.of(city));
        when(cityRepository.save(any())).thenReturn(city);


        CityDto cityDto = cityService.editCity(new CityDto()
                .setId(1L)
                .setName("name1")
                .setPhoto("http://www.photo.com"));

        verify(cityRepository).findById(any());
        verify(cityRepository, atLeastOnce()).findById(any());
        verify(cityRepository).save(any());
        verify(cityRepository, atLeastOnce()).save(any());

        Assertions.assertEquals("name1", cityDto.getName());
        Assertions.assertEquals("http://www.photo.com", cityDto.getPhoto());
    }

    @Test
    void should_editCityEntity_exception() {
        City city = new City().setId(1L).setName("name1").setPhoto("photo1").setVersion(0);

        when(cityRepository.findById(any())).thenReturn(Optional.empty());
        when(cityRepository.save(any())).thenReturn(city);

        Assertions.assertThrows(ServiceException.class, () -> cityService.editCity(new CityDto()));

        verify(cityRepository).findById(any());
        verify(cityRepository, atLeastOnce()).findById(any());
        verify(cityRepository, never()).save(any());
    }


}
