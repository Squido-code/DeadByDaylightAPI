package com.guillermo.DeadByDaylightAPI.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity(name = "survivor")
public class Survivor {
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
    private int rating;
    private List<Perk> perkList;
}
