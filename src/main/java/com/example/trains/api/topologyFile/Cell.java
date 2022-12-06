package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cell implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("type")
    private String type;
    @JsonProperty("state")
    private State state;
    public ArrayList<int[]> getArrayDirection(){
        ArrayList<int[]> array = new ArrayList<>();
        if (state != null) {
            Rail railState = (Rail) state.getInfo();
            if (railState.isX()) array.add(new int[]{2, 6});
            if (railState.isY()) array.add(new int[]{0, 4});
            if (railState.isDx()) array.add(new int[]{3, 7});
            if (railState.isDy()) array.add(new int[]{1, 5});
            if (railState.isRx_top()) array.add(new int[]{2, 7});
            if (railState.isRx_down()) array.add(new int[]{3, 6});
            if (railState.isRx_left()) array.add(new int[]{2, 5});
            if (railState.isRx_right()) array.add(new int[]{1, 6});
            if (railState.isRy_top()) array.add(new int[]{0, 3});
            if (railState.isRy_down()) array.add(new int[]{4, 7});
            if (railState.isRy_left()) array.add(new int[]{0, 5});
            if (railState.isRy_right()) array.add(new int[]{1, 4});
        }
        return array;
    }
    public Cell (int id, int x, int y, State state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
    }

}
