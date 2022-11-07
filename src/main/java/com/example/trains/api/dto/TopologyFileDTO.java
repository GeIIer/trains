package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TopologyFileDTO implements Serializable {
    @NonNull
    private String title;

    @NonNull
    private ArrayList<ArrayList<Cell>> body;

}
