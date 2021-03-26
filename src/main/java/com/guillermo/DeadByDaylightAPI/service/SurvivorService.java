package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;


import java.util.Set;

public interface SurvivorService {
    Set<Survivor> findAll();
    Survivor findByName(String name);
}
