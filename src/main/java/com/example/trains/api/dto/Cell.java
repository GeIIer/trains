package com.example.trains.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Cell implements Serializable {
    @NonNull
    private int id;
    @NonNull
    private int x;
    @NonNull
    private int y;

    private String type;

    private State state;
    public ArrayList<int[]> getArrayDirection(){
        ArrayList<int[]> array = new ArrayList<>();
        if(state.isX()) array.add(new int[] {2,6});
        if(state.isY()) array.add(new int[] {0,4});
        if(state.isDx()) array.add(new int[] {3,7});
        if(state.isDy()) array.add(new int[] {1,5});
        if(state.isRx_top()) array.add(new int[] {2,7});
        if(state.isRx_down()) array.add(new int[] {3,6});
        if(state.isRx_left()) array.add(new int[] {2,5});
        if(state.isRx_right()) array.add(new int[] {1,6});
        if(state.isRy_top()) array.add(new int[] {0,3});
        if(state.isRy_down()) array.add(new int[] {4,7});
        if(state.isRy_left()) array.add(new int[] {0,5});
        if(state.isRy_right()) array.add(new int[] {1,4});

        return array;
    }
    public Cell (int id, int x, int y, State state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
    }

}
