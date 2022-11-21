package com.example.trains.api.dto;

import lombok.*;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TopologyDTO {
    @NonNull
    private Long idTopology;
    @NonNull
    private String topologyName;
    @NonNull
    private String accountName;
    @NonNull
    private String city;
}
