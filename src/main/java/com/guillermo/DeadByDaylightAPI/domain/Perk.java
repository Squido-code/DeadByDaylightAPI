package com.guillermo.DeadByDaylightAPI.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;


@Data
@Builder
@Entity(name = "perk")
public class Perk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String versionNumber;
    @Column
    private String comment;
    @Column
    private Boolean exhaustion;

}
