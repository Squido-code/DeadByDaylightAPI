package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DlcRepository extends CrudRepository<Dlc,Long> {
    Set<Dlc> findAll();
    Dlc findByName(String name);
}
