package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;


import java.util.Optional;
import java.util.Set;

public interface SurvivorService {
    Set<Survivor> findAll();
    Set<Survivor> findByNationality(String nationality);
    Set<Survivor> findByRating(String rating);
    Set<Survivor> findByreleaseDate(String date);
    Survivor findById(long id);

    Survivor addSurvivor(Survivor survivor);
    void deletedById(long id);
    Survivor modifySurvivor(long id, Survivor newSurvivor);
}
