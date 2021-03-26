package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PerkRepository extends CrudRepository<Perk,Long> {
    Set<Perk> findAll();
    Set<Perk> findByExhaustion(Boolean exhaustion);
    Set<Perk> findByVersionNumber(String version);
    Set<Perk> findBySurvivor(Survivor survivor);
    Optional<Perk> findByName(String name);
}
