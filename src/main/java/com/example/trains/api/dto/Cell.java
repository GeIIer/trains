package com.example.trains.api.dto;

public class Cell {
    private int idCell;
    private int x;
    private int y;
    //xyi poi-mi sho za state, voobse eto object


    public Cell(int idCell, int x, int y) {
        this.idCell = idCell;
        this.x = x;
        this.y = y;
    }

    public void setIdCell(int idCell) {
        this.idCell = idCell;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIdCell() {
        return idCell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
