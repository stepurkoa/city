package com.city.list.integration;

import com.city.list.data.TestData;
import com.city.list.dto.CityDto;
import com.city.list.mapper.CityMapper;
import com.city.list.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITCityApi {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityMapper mapper;

    @Container
    public static CustomMySqlContainer mySqlContainer = CustomMySqlContainer.getInstance()
            .withExposedPorts(3306)
            .withInitScript("schema.sql");

    @Test
    public void IT_returnPageWithCityDtos() {
        ResponseEntity<String> customerResponse = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity("/api/v1/cities?page=0&size=10", String.class);

        assertThat(customerResponse.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void IT_returnUnauthorized_401() {
        ResponseEntity<String> customerResponse = restTemplate
                .getForEntity("/api/v1/cities?page=0&size=10", String.class);
        assertThat(customerResponse.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void IT_returnPageWithCityDtos_with_nameContainsSearchTest() {
        ResponseEntity<String> customerResponse = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity("/api/v1/cities/od?page=0&size=10", String.class);
        assertThat(customerResponse.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void IT_editCity() {
        HttpEntity<CityDto> request = new HttpEntity<>(TestData.cityDto.setId(1L).setName("name"));

        ResponseEntity<String> responce = restTemplate
                .withBasicAuth("admin", "admin")
                .exchange("/api/v1/cities", HttpMethod.PUT, request, String.class);
        assertThat(responce.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void IT_returnBadRequest400() {
        HttpEntity<CityDto> request = new HttpEntity<>(TestData.cityDto.setPhoto("testPhoto"));

        ResponseEntity<String> responce = restTemplate
                .withBasicAuth("admin", "admin")
                .exchange("/api/v1/cities", HttpMethod.PUT, request, String.class);
        assertThat(responce.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @Test
    public void IT_returnForbidden403() {
        HttpEntity<CityDto> request = new HttpEntity<>(new CityDto()
                .setId(1L)
                .setName("string")
                .setPhoto("http://www.photo.com"));
        ResponseEntity<String> responce = restTemplate
                .withBasicAuth("user", "user")
                .exchange("/api/v1/cities", HttpMethod.PUT, request, String.class);
        assertThat(responce.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

}