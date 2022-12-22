package com.example.trains.api.service;

import com.example.trains.api.dto.RecordAndWayDTO;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.timetableFile.Record;
import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Rail;
import com.example.trains.api.topologyFile.Step;
import com.example.trains.api.topologyFile.TopologyFileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FindWayService {
    ArrayList<Step> way = new ArrayList<>();
    ArrayList<ArrayList<Step>> ways = new ArrayList<>();
    int endX;
    int endY;
    TopologyFileDTO topology = new TopologyFileDTO();
    public ArrayList<Step> getWay(Step start, Cell end, ArrayList<Cell> stops, TopologyFileDTO topology){
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
            ways.clear();
            rec(step);
            if (ways.size() == 0)
                return null;
            int sizeWay = ways.get(0).size();
            int x = 0;
            for(int j = 1; j<ways.size(); j++){
                if(ways.get(j).size()<sizeWay){
                    x=j;
                    sizeWay=ways.get(j).size();
                }
            }
            endWay.addAll(ways.get(x));
        }
        return endWay;
    }
    public void rec(Step step){
        way.add(step);
        if ((step.getX()==endX)&&(step.getY()==endY)){
            ways.add(new ArrayList<Step>(way));
            return;
        }
        Cell cell = topology.getCell(step.getX(),step.getY());
        if(cell == null) return;
        ArrayList<int[]> arrayDirection = cell.getArrayDirection();
        if (arrayDirection.size() == 0) return;
        int revDirection;
        if(step.getDir()>3) revDirection=step.getDir()-4;
        else revDirection=step.getDir()+4;
        for(int i=0; i<arrayDirection.size(); i++){
            if((arrayDirection.get(i)[0]==revDirection)||(arrayDirection.get(i)[1]==revDirection)){
                int newDirection;
                if(arrayDirection.get(i)[0]==revDirection) newDirection = arrayDirection.get(i)[1];
                else newDirection = arrayDirection.get(i)[0];
                int[] newXY = getNewXY(step.getX(),step.getY(),newDirection);
                Step newStep = new Step(newXY[0], newXY[1], newDirection);
                if(!way.contains(newStep)){
                    rec(newStep);
                    way.remove(way.size()-1);
                }
            }
        }
    }
    private int[] getNewXY(int x,int y, int direction){
        switch(direction){
            case 0:
                return new int[] {x,y+1};
            case 1:
                return new int[] {x+1,y+1};
            case 2:
                return new int[] {x+1,y};
            case 3:
                return new int[] {x+1,y-1};
            case 4:
                return new int[] {x,y-1};
            case 5:
                return new int[] {x-1,y-1};
            case 6:
                return new int[] {x-1,y};
            case 7:
                return new int[] {x-1,y+1};
        }
        return null;
    }
    //TODO Оптимизировать говнокод
    public ArrayList<RecordAndWayDTO> getRecordsAndWays (ArrayList<Record> records, TopologyFileDTO topologyFileDTO) {
        try {
            ArrayList<RecordAndWayDTO> recordAndWayDTOS = new ArrayList<>();
            int lengthX = topologyFileDTO.getBody().size();
            int lengthY = topologyFileDTO.getBody().get(0).size();
            for (int i = 0; i < records.size(); i++) {
                int xPlateLine = records.get(i).getPlateLine().getX();
                int yPlateLine = records.get(i).getPlateLine().getY();
                Cell stop = topologyFileDTO.getCell(xPlateLine, yPlateLine);
                Cell exit = records.get(i).getOut();
                int x = records.get(i).getIn().getX();
                int y = records.get(i).getIn().getY();
                int dir = ((Rail) records.get(i).getIn().getState()).getDir(x, y, lengthX, lengthY);
                ArrayList<Step> ways = getWay(new Step(x, y, dir),
                        exit,
                        new ArrayList<>(Arrays.asList(stop)),
                        topologyFileDTO);
                if (ways == null)
                {
                    throw new RuntimeException("Невозможно проложить путь по заданному маршруту: " + (i+1));
                }
                recordAndWayDTOS.add(new RecordAndWayDTO(records.get(i), ways));
            }
            return recordAndWayDTOS;
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException();
    }
}
