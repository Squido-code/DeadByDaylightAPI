package com.guillermo.DeadByDaylightAPI.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Entity(name = "dlc")
public class Dlc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private Survivor survivor;
    @Column
    private Killer killer;
    @Column
    private int chapterNumber;
    @Column
    private LocalDate releaseDate;
}
