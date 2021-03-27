package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface KillerService {
    Set<Killer> findAll();
    Set<Killer> findByDifficulty(String difficult);
    Set<Killer> findByPower(String power);
    Set<Killer> findByReleaseDate(String date);
    Killer findByName(String name);
    Killer findById(long id);

    Killer addKiller(Killer killer);
    void deletedById(long id);
    Killer modifyKiller(long id, Killer newKiller);
}
