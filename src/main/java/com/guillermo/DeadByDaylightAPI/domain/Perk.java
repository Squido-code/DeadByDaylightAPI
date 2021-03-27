package com.guillermo.DeadByDaylightAPI.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne
    @JoinColumn(name = "survivor_id" )
    @JsonBackReference
    private Survivor survivor;

}
