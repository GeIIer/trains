package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.core.JsonGenerator;
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
        CellSerializer cellSerializer = new CellSerializer();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("title", topology.getTitle());

        jsonGenerator.writeArrayFieldStart("body");
        for (ArrayList<Cell> cellArray: topology.getBody()) {
            jsonGenerator.writeStartArray();
            for (int i = 0; i < cellArray.size(); i++) {
                Cell cell = cellArray.get(i);
                cellSerializer.serialize(cell, jsonGenerator, serializerProvider);
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
