package com.guillermo.DeadByDaylightAPI.domain;


import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "survivor_id" )
    private Survivor survivor;

}
