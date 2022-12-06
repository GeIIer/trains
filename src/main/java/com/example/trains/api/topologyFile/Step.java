package com.example.trains.api.topologyFile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Step {
    int x;
    int y;
    int direction;
}
