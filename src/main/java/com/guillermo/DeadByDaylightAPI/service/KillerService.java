package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface KillerService {
    Set<Killer> findAll();
    Set<Killer> findByDifficulty(String difficult);
    Set<Killer> findByPower(String power);
    Set<Killer> findByReleaseDate(String date);
    Killer findByName(String name);
}
