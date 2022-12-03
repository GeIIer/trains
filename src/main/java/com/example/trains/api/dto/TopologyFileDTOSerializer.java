package com.example.trains.api.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;

public class TopologyFileDTOSerializer extends StdSerializer<TopologyFileDTO > {
    public TopologyFileDTOSerializer (Class<TopologyFileDTO> t) {
        super(t);
    }

    public TopologyFileDTOSerializer() {
        this(null);
    }

    @Override
    public void serialize(TopologyFileDTO topology, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("title", topology.getTitle());

        jsonGenerator.writeArrayFieldStart("body");
        for (ArrayList<Cell> cellArray: topology.getBody()) {
            jsonGenerator.writeStartArray();
            for (int i = 0; i < cellArray.size(); i++) {
                Cell cell = cellArray.get(i);
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("id", String.valueOf(cell.getId()));
                jsonGenerator.writeStringField("x", String.valueOf(cell.getX()));
                jsonGenerator.writeStringField("y", String.valueOf(cell.getY()));
                jsonGenerator.writeStringField("type", cell.getType());
                if (cell.getType().equals("rail")) {
                    Rail rail = (Rail) cell.getState().getInfo();
                    jsonGenerator.writeObjectFieldStart("state");

                    jsonGenerator.writeStringField("x", String.valueOf(rail.isX()));
                    jsonGenerator.writeStringField("y", String.valueOf(rail.isY()));
                    jsonGenerator.writeStringField("dx", String.valueOf(rail.isDx()));
                    jsonGenerator.writeStringField("dy", String.valueOf(rail.isDy()));
                    jsonGenerator.writeStringField("rx_top", String.valueOf(rail.isRx_top()));
                    jsonGenerator.writeStringField("rx_down", String.valueOf(rail.isRx_down()));
                    jsonGenerator.writeStringField("rx_left", String.valueOf(rail.isRx_left()));
                    jsonGenerator.writeStringField("rx_right", String.valueOf(rail.isRx_right()));
                    jsonGenerator.writeStringField("ry_top", String.valueOf(rail.isRy_top()));
                    jsonGenerator.writeStringField("ry_down", String.valueOf(rail.isRy_down()));
                    jsonGenerator.writeStringField("ry_left", String.valueOf(rail.isRy_left()));
                    jsonGenerator.writeStringField("ry_right", String.valueOf(rail.isRy_right()));

                    jsonGenerator.writeEndObject();
                }
                else if (cell.getType().equals("plate")) {
                    Plate plate = (Plate) cell.getState().getInfo();
                    jsonGenerator.writeObjectFieldStart("state");
                    jsonGenerator.writeStringField("dir", String.valueOf(plate.isDir()));
                    //line1
                    jsonGenerator.writeObjectFieldStart("line1");
                    jsonGenerator.writeNumberField("x", plate.getLine1().getX());
                    jsonGenerator.writeNumberField("y", plate.getLine1().getY());
                    jsonGenerator.writeNumberField("number", plate.getLine1().getNumber());
                    jsonGenerator.writeEndObject();
                    //line2
                    jsonGenerator.writeObjectFieldStart("line2");
                    jsonGenerator.writeNumberField("x", plate.getLine2().getX());
                    jsonGenerator.writeNumberField("y", plate.getLine2().getY());
                    jsonGenerator.writeNumberField("number", plate.getLine2().getNumber());
                    jsonGenerator.writeEndObject();

                    jsonGenerator.writeNumberField("number", plate.getNumber());
                    jsonGenerator.writeEndObject();

                }
                else {
                    jsonGenerator.writeObjectFieldStart("state");
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
