package com.example.trains.authorization.dto;

import lombok.*;

import java.util.List;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String role;
}
