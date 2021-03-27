package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.repository.KillerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public Set<Killer> findByDifficulty(String difficult) {
        return killerRepository.findByDifficulty(difficult);
    }

    @Override
    public Set<Killer> findByPower(String power) {
        return killerRepository.findByPower(power);
    }

    @Override
    public Set<Killer> findByReleaseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);
        return killerRepository.findByReleaseDate(localDate);
    }

    @Override
    public Killer findByName(String name) {
        return killerRepository.findByName(name);
    }
}
