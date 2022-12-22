package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Step implements Serializable {
    @JsonProperty("x")
    int x;
    @JsonProperty("y")
    int y;
    @JsonProperty("dir")
    int dir;
}
