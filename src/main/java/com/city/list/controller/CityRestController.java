package com.city.list.controller;

import com.city.list.dto.CustomPage;
import com.city.list.service.CityService;
import com.city.list.dto.CityDto;
import com.city.list.validator.Validators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
@Slf4j
public class CityRestController {

    private final CityService cityService;
    private final Validators validators;

    @GetMapping
    public CustomPage<CityDto> getCities(@RequestParam int page, @RequestParam int size) {
        log.info("Run method get cities with pagination.");
        return cityService.getCities(PageRequest.of(page, size));
    }
    @GetMapping("/{text}")
    public CustomPage<CityDto> searchCitiesByName(@PathVariable String text, @RequestParam int page, @RequestParam int size) {
        log.info("Run method search cities by text.");
        return cityService.searchCitiesByName(text, PageRequest.of(page, size));
    }

    @PutMapping
    public CityDto editCity(@RequestBody CityDto cityDto) {
        log.info("Run method edit city.");
        validators.urlValidator(cityDto.getPhoto());
        return cityService.editCity(cityDto);
    }
}