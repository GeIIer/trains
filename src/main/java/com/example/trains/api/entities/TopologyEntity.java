package com.example.trains.api.entities;

import com.example.trains.authorization.entities.AccountEntity;
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
@Table(name = "topology")
public class TopologyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTopology;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private AccountEntity account;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private CityEntity city;

    private String filename;

}
