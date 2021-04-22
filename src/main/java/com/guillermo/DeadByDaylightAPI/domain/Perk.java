package com.guillermo.DeadByDaylightAPI.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "perk")
public class Perk {
    @Schema(description = "Perk identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Schema(description = "Ability name", example = "Bond", required = true)
    @Column
    private String name;
    @Schema(description = "Ability description", example = "Strengthens the potential of you and your team's Aura-reading...", required = true)
    @Column
    private String description;
    @Schema(description = "Last update", example = "2.3.0", required = false)
    @Column
    private String versionNumber;
    @Schema(description = "Survivor comment", example = "I have true sight. — Feng Min", required = false)
    @Column
    private String comment;
    @Schema(description = "Use or not of the exhaustion property", example = "true", required = true)
    @Column
    private Boolean exhaustion;
    @Schema(description = "Who has the perk", example = "Feng Min", required = true)
    @ManyToOne
    @JoinColumn(name = "survivor" )
    @JsonBackReference
    private Survivor survivor;

}
