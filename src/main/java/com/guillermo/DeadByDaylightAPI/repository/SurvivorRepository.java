package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SurvivorRepository extends CrudRepository<Survivor,Long> {
    Set<Survivor> findAll();
    Survivor findByName(String name);

}
