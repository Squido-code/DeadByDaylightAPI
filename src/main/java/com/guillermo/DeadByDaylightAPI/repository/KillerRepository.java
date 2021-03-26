package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface KillerRepository extends CrudRepository<Killer,Long> {
    Set<Killer> findAll();
    Killer findByName(String name);
}
