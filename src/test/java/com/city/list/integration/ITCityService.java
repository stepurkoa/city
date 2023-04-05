package com.city.list.integration;

import com.city.list.data.TestData;
import com.city.list.dto.CityDto;
import com.city.list.dto.CustomPage;
import com.city.list.exceptions.ServiceException;
import com.city.list.service.CityService;
import com.city.list.validator.Validators;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class ITCityService {

    @Autowired
    private CityService cityService;

    @Container
    public static CustomMySqlContainer mySqlContainer =
            CustomMySqlContainer.getInstance()
                    .withExposedPorts(3306)
                    .withInitScript("schema.sql");

    @Test
    public void IT_returnCities() {
        CustomPage<CityDto> cities = cityService.getCities(PageRequest.of(0, 10));
        Assertions.assertEquals(4, cities.getContent().size());
    }

    @Test
    public void IT_returnCities_count2() {
        CustomPage<CityDto> cities = cityService.getCities(PageRequest.of(0, 2));
        Assertions.assertEquals(2, cities.getContent().size());
    }

    @Test
    public void IT_returnCities_with_NameContainSearchText_returnOneCity() {
        CustomPage<CityDto> cities = cityService.searchCitiesByName("Nitra", PageRequest.of(0, 10));
        Assertions.assertEquals(1, cities.getContent().size());
        Assertions.assertEquals("Nitra", cities.getContent().get(0).getName());
    }

    @Test
    public void IT_returnCities_with_NameContainSearchText_returnNothing() {
        CustomPage<CityDto> cities = cityService.searchCitiesByName("CityNew", PageRequest.of(0, 10));
        Assertions.assertEquals(0, cities.getContent().size());
    }

    @Test
    public void IT_updateCities() {
        CityDto city = TestData.cityDto.setId(1L);
        CityDto cityUpdate = cityService.editCity(city);
        Assertions.assertEquals(city.getName(), cityUpdate.getName());
        Assertions.assertEquals(city.getPhoto(), cityUpdate.getPhoto());
    }

    @Test
    public void IT_updateCitiesNullName_validationException() {
        Assertions.assertThrows(TransactionSystemException.class,
                () -> cityService.editCity(TestData.cityDto.setName(null)));
    }

    @Test
    public void IT_updateCities_excheption() {
        Assertions.assertThrows(ServiceException.class,
                () -> cityService.editCity(TestData.cityDto.setId(1001L)));
    }
}