package com.guillermo.DeadByDaylightAPI.domain;

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

    @OneToMany(mappedBy = "survivor")
    private List<Perk> perkList = new ArrayList<>();
}
