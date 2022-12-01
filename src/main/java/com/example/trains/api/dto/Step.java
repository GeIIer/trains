package com.example.trains.api.dto;

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
