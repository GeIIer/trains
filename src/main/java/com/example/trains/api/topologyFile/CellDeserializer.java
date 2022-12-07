package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CellDeserializer extends StdDeserializer<Cell> {
    public CellDeserializer() {
        this(null);
    }

    @Override
    public Cell deserialize(JsonParser jp, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        return getCell(node);
    }

    public CellDeserializer(Class<Cell> vc) {
        super(vc);
    }

    public Cell getCell(JsonNode jsonNode) {
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
