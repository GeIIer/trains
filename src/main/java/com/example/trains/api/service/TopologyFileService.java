package com.example.trains.api.service;

import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Plate;
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
        for (int i = 0; i < matrix.get(0).size(); i++) {
            matrix.get(0).get(i);
        }
        return cells;
    }
}
