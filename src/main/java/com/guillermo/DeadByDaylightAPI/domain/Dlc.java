package com.guillermo.DeadByDaylightAPI.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "DlC identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Schema(description = "DLC name", example = "Flesh and mud", required = true)
    @Column
    private String name;
    @Schema(description = "DLC price in Steam", example = "6.99", required = false)
    @Column
    private double price;
    @Schema(description = "Survivor that belong to the DLC", example = "Dwight Fairfield", required = true)
    @ManyToOne
    @JoinColumn(name = "survivor")
    private Survivor survivor;
    @Schema(description = "Killer that belongs to the DLC", example = "The trapper", required = true)
    @ManyToOne
    @JoinColumn(name = "killer")
    private Killer killer;
    @Schema(description = "DLC number", example = "1", required = true)
    @Column
    private int chapterNumber;
    @Schema(description = "Release date", example = "2016-06-04", required = true)
    @Column
    private LocalDate releaseDate;
}
