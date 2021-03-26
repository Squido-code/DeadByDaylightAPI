package com.guillermo.DeadByDaylightAPI.domain;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "dlc")
public class Dlc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private double price;
    @ManyToOne
    @JoinColumn(name = "survivor")
    private Survivor survivor;
    @ManyToOne
    @JoinColumn(name = "killer")
    private Killer killer;
    @Column
    private int chapterNumber;
    @Column
    private LocalDate releaseDate;
}
