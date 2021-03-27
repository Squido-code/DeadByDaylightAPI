package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface KillerRepository extends CrudRepository<Killer,Long> {
    Set<Killer> findAll();
    Set<Killer> findByDifficulty(String dificult);
    Set<Killer> findByPower(String power);
    Set<Killer> findByReleaseDate(LocalDate localDate);
    Killer findByName(String name);
    Optional<Killer> findById(long id);
}
