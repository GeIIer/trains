package com.example.trains.api.timetableFile;

import com.example.trains.api.topologyFile.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PlatesAndInOutSerializer extends StdSerializer<PlatesAndInOut> {

    public PlatesAndInOutSerializer (Class<PlatesAndInOut> t) {
        super(t);
    }

    public PlatesAndInOutSerializer() {
        this(null);
    }
    @Override
    public void serialize(PlatesAndInOut platesAndInOut, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        CellSerializer cellSerializer = new CellSerializer();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("inOut");
        for (int i = 0; i < platesAndInOut.getInOut().size(); i++) {
            Cell cell = platesAndInOut.getInOut().get(i);
            cellSerializer.serialize(cell, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("plates");
        for (int i = 0; i < platesAndInOut.getPlates().size(); i++) {
            Plate plate = platesAndInOut.getPlates().get(i);
            jsonGenerator.writeStartObject();
            cellSerializer.plateSerialize(plate, jsonGenerator);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
