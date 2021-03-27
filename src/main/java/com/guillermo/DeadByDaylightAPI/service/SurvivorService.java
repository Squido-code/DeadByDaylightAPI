package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;



import java.util.Set;

public interface SurvivorService {
    Set<Survivor> findAll();
    Set<Survivor> findByNationality(String nationality);
    Set<Survivor> findByRating(String rating);
    Set<Survivor> findByreleaseDate(String date);
    Survivor findByName(String name);
}
