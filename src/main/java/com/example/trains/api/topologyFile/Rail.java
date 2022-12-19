package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rail extends State implements Serializable {
    @JsonProperty("x")
    private boolean x;
    @JsonProperty("y")
    private boolean y;
    @JsonProperty("dx")
    private boolean dx;
    @JsonProperty("dy")
    private boolean dy;
    @JsonProperty("rx_top")
    private boolean rx_top;
    @JsonProperty("rx_down")
    private boolean rx_down;
    @JsonProperty("rx_left")
    private boolean rx_left;
    @JsonProperty("rx_right")
    private boolean rx_right;
    @JsonProperty("ry_top")
    private boolean ry_top;
    @JsonProperty("ry_down")
    private boolean ry_down;
    @JsonProperty("ry_left")
    private boolean ry_left;
    @JsonProperty("ry_right")
    private boolean ry_right;
    @JsonProperty("light")
    private boolean light;

    @Override
    public void setInfo(JsonNode jsonNode) {
        if (jsonNode.has("x")) this.setX(jsonNode.get("x").asBoolean());
        if (jsonNode.has("y")) this.setY(jsonNode.get("y").asBoolean());
        if (jsonNode.has("dx")) this.setDx(jsonNode.get("dx").asBoolean());
        if (jsonNode.has("dy")) this.setDy(jsonNode.get("dy").asBoolean());
        if (jsonNode.has("rx_top")) this.setRx_top(jsonNode.get("rx_top").asBoolean());
        if (jsonNode.has("rx_down")) this.setRx_down(jsonNode.get("rx_down").asBoolean());
        if (jsonNode.has("rx_left")) this.setRx_left(jsonNode.get("rx_left").asBoolean());
        if (jsonNode.has("rx_right")) this.setRx_right(jsonNode.get("rx_right").asBoolean());
        if (jsonNode.has("ry_top")) this.setRy_top(jsonNode.get("ry_top").asBoolean());
        if (jsonNode.has("ry_down")) this.setRy_down(jsonNode.get("ry_down").asBoolean());
        if (jsonNode.has("ry_left")) this.setRy_left(jsonNode.get("ry_left").asBoolean());
        if (jsonNode.has("ry_right")) this.setRy_right(jsonNode.get("ry_right").asBoolean());
        if (jsonNode.has("light")) this.setLight(jsonNode.get("light").asBoolean());
    }

    @Override
    public Object getInfo() {
        return this;
    }
}
