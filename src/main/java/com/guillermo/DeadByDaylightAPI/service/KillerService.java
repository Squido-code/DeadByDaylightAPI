package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface KillerService {
    Set<Killer> findAll();
    Killer findByName(String name);
}
