package com.example.trains.api.topologyFile;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JsonNode;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Rail.class, name="rail"),
        @JsonSubTypes.Type(value=Plate.class, name="plate"),
        @JsonSubTypes.Type(value=State.class, name="none")
})
public abstract class State {
    public abstract void setInfo(JsonNode jsonNode);

    public abstract Object getInfo();
}