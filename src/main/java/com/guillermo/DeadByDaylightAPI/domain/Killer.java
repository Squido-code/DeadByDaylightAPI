package com.guillermo.DeadByDaylightAPI.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "killer")
public class Killer {
    @Schema(description = "Killer identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Schema(description = "Killer name", example = "The Trapper", required = true)
    @Column
    private String name;
    @Schema(description = "Release date", example = "2016-06-04", required = false)
    @Column
    private LocalDate releaseDate;
    @Schema(description = "Killer story", example = "Evan MacMillan idolised his father. It wasn't just that he was heir to a great fortune..", required = false)
    @Column
    private String lore;
    @Schema(description = "Difficulty", example = "Easy", required = true)
    @Column
    private String difficulty;
    @Schema(description = "Killer´s power", example = "Bear trap", required = true)
    @Column
    private String power;

}
