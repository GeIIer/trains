package com.example.trains.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@NoArgsConstructor
public class State implements Serializable {
    private boolean x;
    private boolean y;
    private boolean dx;
    private boolean dy;
    private boolean rx_top;
    private boolean rx_down;
    private boolean rx_left;
    private boolean rx_right;
    private boolean ry_top;
    private boolean ry_down;
    private boolean ry_left;
    private boolean ry_right;
}
