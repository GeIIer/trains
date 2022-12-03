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
                jsonGenerator.writeNumberField("id", cell.getId());
                jsonGenerator.writeNumberField("x", cell.getX());
                jsonGenerator.writeNumberField("y", cell.getY());
                jsonGenerator.writeStringField("type", cell.getType());
                if (cell.getType().equals("rail")) {
                    Rail rail = (Rail) cell.getState().getInfo();
                    jsonGenerator.writeObjectFieldStart("state");

                    jsonGenerator.writeBooleanField("x", rail.isX());
                    jsonGenerator.writeBooleanField("y", rail.isY());
                    jsonGenerator.writeBooleanField("dx", rail.isDx());
                    jsonGenerator.writeBooleanField("dy", rail.isDy());
                    jsonGenerator.writeBooleanField("rx_top", rail.isRx_top());
                    jsonGenerator.writeBooleanField("rx_down", rail.isRx_down());
                    jsonGenerator.writeBooleanField("rx_left", rail.isRx_left());
                    jsonGenerator.writeBooleanField("rx_right", rail.isRx_right());
                    jsonGenerator.writeBooleanField("ry_top", rail.isRy_top());
                    jsonGenerator.writeBooleanField("ry_down", rail.isRy_down());
                    jsonGenerator.writeBooleanField("ry_left", rail.isRy_left());
                    jsonGenerator.writeBooleanField("ry_right", rail.isRy_right());

                    jsonGenerator.writeEndObject();
                }
                else if (cell.getType().equals("plate")) {
                    Plate plate = (Plate) cell.getState().getInfo();
                    jsonGenerator.writeObjectFieldStart("state");
                    jsonGenerator.writeBooleanField("dir", plate.isDir());
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
