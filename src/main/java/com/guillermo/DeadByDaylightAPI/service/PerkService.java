package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;


import java.util.Optional;
import java.util.Set;

public interface PerkService {
    Set<Perk> findAll();
    Set<Perk> findByExhaustion(Boolean  exhaustion);
    Set<Perk> findByVersionNumber(String version);
    Set<Perk> findBySurvivor(String survivor);
    Optional<Perk>findByName(String name);
    Perk findById(long id);

    Perk addPerk(Perk perk);
    void deletedById(long id);
    Perk modifyPerk(long id, Perk newPerk);

}
