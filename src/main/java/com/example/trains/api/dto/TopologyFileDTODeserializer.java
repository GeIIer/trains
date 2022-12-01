package com.example.trains.api.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;

public class TopologyFileDTODeserializer extends StdDeserializer<TopologyFileDTO > {
    public TopologyFileDTODeserializer() {
        this(null);
    }

    public TopologyFileDTODeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TopologyFileDTO deserialize(JsonParser jp, DeserializationContext ds)
            throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        TopologyFileDTO topologyFileDTO = new TopologyFileDTO();
        String title = (String) node.get("title").asText();
        topologyFileDTO.setTitle(title);
        var body = node.get("body");
        int matrixLength = body.size();
        ArrayList<ArrayList<Cell>> matrix = new ArrayList<ArrayList<Cell>>();
        int id = 0;
        while (id < matrixLength) {
            int j = 0;
            int arrayLength = body.get(id).size();
            ArrayList<Cell> array = new ArrayList<Cell>();
            while (j < arrayLength) {
                array.add(getCell(body.get(id).get(j)));
                j++;
            }
            matrix.add(array);
            id++;
        }
        topologyFileDTO.setBody(matrix);
        return topologyFileDTO;
    }

    private Cell getCell(JsonNode jsonNode) {
        Cell cell = new Cell();
        cell.setId(jsonNode.get("id").asInt());
        cell.setX(jsonNode.get("x").asInt());
        cell.setY(jsonNode.get("y").asInt());
        cell.setType(jsonNode.get("type").asText());
        if (cell.getType().equals("rail")) {
            State rail = new Rail();
            JsonNode JsonRail = jsonNode.get("state");
            rail.setInfo(JsonRail);
            cell.setState(rail);
        }
        else if (cell.getType().equals("plate")) {
            State plate = new Plate();
            JsonNode JsonRail = jsonNode.get("state");
            plate.setInfo(JsonRail);
            cell.setState(plate);
        }
        else {
            cell.setState(null);
        }
        return cell;
    }
}
