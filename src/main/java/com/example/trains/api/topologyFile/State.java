package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class State {
    public abstract void setInfo(JsonNode jsonNode);

    public abstract Object getInfo();
}
