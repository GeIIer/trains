package com.example.trains.api.service;

import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Plate;
import com.example.trains.api.topologyFile.Rail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TopologyFileService {

    public ArrayList<Plate> getPlates (ArrayList<ArrayList<Cell>> matrix) {
        ArrayList<Plate> plates = new ArrayList<>();
        for (ArrayList<Cell> array : matrix) {
            for (Cell var : array) {
                if (var.getType().equals("plate")) {
                    plates.add((Plate) var.getState().getInfo());
                }
            }
        }
        return plates;
    }
    //TODO
    public ArrayList<Cell> getInOut(ArrayList<ArrayList<Cell>> matrix) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            int z = 0;
            for(int j=0; j<2;j++) {
                Cell x = matrix.get(i).get(z);
                if (x.getType().equals("rail")) {
                    Rail r = (Rail) x.getState().getInfo();
                    if (r.isY()) cells.add(x);
                }
                z=matrix.get(i).size()-1;
            }
        }
        int i = 0;
        for (int z = 0; z < 2; z++) {
            for(int j=0; j<matrix.get(i).size(); j++) {
                Cell x = matrix.get(i).get(j);
                if (x.getType().equals("rail")) {
                    Rail r = (Rail) x.getState().getInfo();
                    if (r.isX()) cells.add(x);
                }
            }
            i=matrix.size()-1;
        }

        return cells;
    }
}
