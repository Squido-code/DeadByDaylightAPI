package com.guillermo.DeadByDaylightAPI.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Builder
@Entity(name = "killer")
public class Killer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private LocalDate releaseDate;
    @Column
    private String lore;
    @Column
    private String difficulty;
    @Column
    private String power;
}
