package com.guillermo.DeadByDaylightAPI.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "survivor")
public class  Survivor {
    @Schema(description = "Survivor identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Schema(description = "Survivor name", example = "Dwight Fairfield", required = false)
    @Column
    private String name;
    @Schema(description = "Release date", example = "2016/06/04", required = true)
    @Column
    private LocalDate releaseDate;
    @Schema(description = "Character story", example = "Dwight was geeky and scrawny through high school. ", required = false)
    @Column
    private String lore;
    @Schema(description = "META score", example = "9", required = false)
    @Column
    private int rating;
    @Schema(description = "National identity", example = "American", required = false)
    @Column
    private String nationality;
    @Schema(description = "Group of exclusive abilities", example = "Bond", required = false)
    @OneToMany(mappedBy = "survivor",cascade = CascadeType.ALL)
    private List<Perk> perkList = new ArrayList<>();
}
