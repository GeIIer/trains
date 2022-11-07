package com.example.trains.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "type_train")
public class TypeTrainsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idType;

    private String typeTrain;
}
