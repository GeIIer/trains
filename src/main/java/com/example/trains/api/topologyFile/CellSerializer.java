package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CellSerializer extends StdSerializer<Cell> {
    public CellSerializer (Class<Cell> t) {
        super(t);
    }

    @Override
    public void serialize(Cell cell, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
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
            plateSerialize(plate, jsonGenerator);
            jsonGenerator.writeEndObject();
        }
        else {
            jsonGenerator.writeObjectFieldStart("state");
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
    }

    public void plateSerialize (Plate plate, JsonGenerator jsonGenerator)
            throws IOException {

        jsonGenerator.writeBooleanField("dir", plate.isDir());
        //lines
        jsonGenerator.writeArrayFieldStart("lines");
        for (PlateLine lines : plate.getLines()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("x", lines.getX());
            jsonGenerator.writeNumberField("y", lines.getY());
            jsonGenerator.writeNumberField("number", lines.getNumber());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeNumberField("number", plate.getNumber());

    }

    public CellSerializer() {
        this(null);
    }
}
