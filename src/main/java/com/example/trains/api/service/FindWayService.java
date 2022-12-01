package com.example.trains.api.service;

import com.example.trains.api.dto.Cell;
import com.example.trains.api.dto.Step;
import com.example.trains.api.dto.TopologyFileDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FindWayService {
    ArrayList<Step> way = new ArrayList<>();
    ArrayList<ArrayList<Step>> ways = new ArrayList<>();
    int endX;
    int endY;
    TopologyFileDTO topology = new TopologyFileDTO();
    Cell cell;
    public ArrayList<Step> getWay(Step start, Cell end, ArrayList<Cell> stops,TopologyFileDTO topology){
        this.topology = topology;
        ArrayList<Step> endWay = new ArrayList<>();
        endWay.add(start);
        stops.add(end);
        for (int i = 0; i<stops.size(); i++){
            endX=stops.get(i).getX();
            endY=stops.get(i).getY();
            Step step = endWay.get(endWay.size()-1);
            endWay.remove(endWay.size()-1);
            way.clear();
            rec(step);
            int sizeWay = ways.get(0).size();
            int x = 0;
            for(int j =1; j<ways.size(); j++){
                if(ways.get(1).size()<sizeWay){
                    x=j;
                    sizeWay=ways.get(1).size();
                }
            }
            endWay.addAll(way);
        }
        return endWay;
    }
    public void rec(Step step){
        way.add(step);
        if ((step.getX()==endX)&&(step.getY()==endY)){
            ways.add(way);
            return;
        }
        ArrayList<int[]> arrayDirrection = cell.getArrayDirection(); //topology.getCell(x,y).getArrayDirection();
        int revDirection;
        if(step.getDirection()>3) revDirection=step.getDirection()-4;
        else revDirection=step.getDirection()+4;
        for(int i=0; i<arrayDirrection.size(); i++){
            if((arrayDirrection.get(i)[0]==revDirection)||(arrayDirrection.get(i)[1]==revDirection)){
                int newDirection;
                if(arrayDirrection.get(i)[0]==revDirection) newDirection = arrayDirrection.get(i)[1];
                else newDirection = arrayDirrection.get(i)[0];
                int[] newXY = getNewXY(step.getX(),step.getY(),newDirection);
                Step newStep = new Step(newXY[0], newXY[1], newDirection);
                if(!way.contains(newStep)){
                    rec(newStep);
                    way.remove(way.size()-1);
                }
            }
        }
    }
    public int[] getNewXY(int x,int y, int dirrection){
        switch(dirrection){
            case 0:
                return new int[] {x+1,y};
            case 1:
                return new int[] {x+1,y+1};
            case 2:
                return new int[] {x,y+1};
            case 3:
                return new int[] {x-1,y+1};
            case 4:
                return new int[] {x-1,y};
            case 5:
                return new int[] {x-1,y-1};
            case 6:
                return new int[] {x,y-1};
            case 7:
                return new int[] {x+1,y-1};
        }
        return null;
    }
}
