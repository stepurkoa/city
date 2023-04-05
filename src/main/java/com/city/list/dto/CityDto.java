package com.city.list.dto;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CityDto {
    private Long id;
    private String name;
    private String photo;
}
