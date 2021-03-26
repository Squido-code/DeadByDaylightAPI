package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;

import com.guillermo.DeadByDaylightAPI.repository.SurvivorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class SurvivorServiceImpl implements SurvivorService{
    @Autowired
    private SurvivorRepository survivorRepository;
    @Override
    public Set<Survivor> findAll() {
        return survivorRepository.findAll();
    }

    @Override
    public Survivor findByName(String name) {
        return survivorRepository.findByName(name);
    }
}
