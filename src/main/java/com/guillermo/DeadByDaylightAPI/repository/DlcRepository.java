package com.guillermo.DeadByDaylightAPI.repository;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DlcRepository extends CrudRepository<Dlc,Long> {
    Set<Dlc> findAll();
    Set<Dlc> findBychapterNumber(int chapter);
    Set<Dlc> findByReleaseDate(LocalDate date);
    Set<Dlc> findByPrice(double price);
    Dlc findByName(String name);
    Optional<Dlc> findById(long id);
}
