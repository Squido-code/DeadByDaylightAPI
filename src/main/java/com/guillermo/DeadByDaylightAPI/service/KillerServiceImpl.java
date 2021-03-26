package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.repository.KillerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class KillerServiceImpl implements KillerService{
    @Autowired
    private KillerRepository killerRepository;
    @Override
    public Set<Killer> findAll() {
        return killerRepository.findAll();
    }

    @Override
    public Killer findByName(String name) {
        return killerRepository.findByName(name);
    }
}
