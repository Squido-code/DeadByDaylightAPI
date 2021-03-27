package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface KillerRepository extends CrudRepository<Killer,Long> {
    Set<Killer> findAll();
    Set<Killer> findByDifficulty(String dificult);
    Set<Killer> findByPower(String power);
    Set<Killer> findByReleaseDate(LocalDate localDate);
    Killer findByName(String name);
}
