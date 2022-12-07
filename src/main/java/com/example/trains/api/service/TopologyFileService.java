package com.example.trains.api.service;

import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Plate;
import com.example.trains.api.topologyFile.Rail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class TopologyFileService {
    //TODO мы поняли что нужен сериализатор, но нам лень
    public ArrayList<Plate> getPlates (ArrayList<ArrayList<Cell>> matrix) {
        ArrayList<Plate> plates = new ArrayList<>();
        for (ArrayList<Cell> array : matrix) {
            for (Cell var : array) {
                if (var.getType().equals("plate")) {
                    Plate plate = (Plate) var.getState().getInfo();
                    plates.add(plate);//plate.getString()"");
                }
            }
        }
        return plates;
    }
    public ArrayList<Cell> checkInOut(int i, int j, ArrayList<ArrayList<Cell>> matrix, ArrayList<Cell> cells, boolean b){
        Cell x = matrix.get(i).get(j);
        if (x.getType().equals("rail")) {
            Rail r = (Rail) x.getState().getInfo();
            if (b) {
                if (r.isY()) cells.add(x);
            }else if (r.isX()) cells.add(x);
        }
        return cells;
    }
    public ArrayList<Cell> getInOut(ArrayList<ArrayList<Cell>> matrix) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            cells = checkInOut(i,0, matrix, cells, true);
            cells = checkInOut(i,matrix.get(i).size()-1, matrix, cells, true);
        }
        for(int j=0; j<matrix.get(0).size(); j++) {
            cells = checkInOut(0,j, matrix, cells, false);
        }
        for(int j=0; j<matrix.get(matrix.size()-1).size(); j++) {
            cells = checkInOut(matrix.size()-1,j, matrix, cells, false);
        }
        cells =new ArrayList<>(new HashSet<>(cells));
        return cells;
    }
}
