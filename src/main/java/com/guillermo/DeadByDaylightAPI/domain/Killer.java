package com.guillermo.DeadByDaylightAPI.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
