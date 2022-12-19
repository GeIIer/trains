package com.example.trains.api.topologyFile;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Step implements Serializable {
    int x;
    int y;
    int direction;
}
