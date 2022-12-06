package com.example.trains.api.timetableFile;

import com.example.trains.api.topologyFile.Cell;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {

    private String cityName;

    private Cell cityCell;
}
