package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityWithCount {
    @JsonProperty("cityName")
    private String cityName;
    @JsonProperty("count")
    private int count;
}
