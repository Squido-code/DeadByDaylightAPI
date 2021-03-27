package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SurvivorRepository extends CrudRepository<Survivor,Long> {
    Set<Survivor> findAll();
    Set<Survivor> findByNationality(String nationality);
    Set<Survivor> findByRating(int rating);
    Set<Survivor> findByReleaseDate(LocalDate date);

    Optional<Survivor> findById(long id);
    Survivor findByName(String name);


}
