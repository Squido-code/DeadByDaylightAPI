package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;

import com.guillermo.DeadByDaylightAPI.repository.SurvivorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
@Service
public class SurvivorServiceImpl implements SurvivorService{
    private static final Logger logger = LoggerFactory.getLogger(SurvivorServiceImpl.class);
    @Autowired
    private SurvivorRepository survivorRepository;
    @Override
    public Set<Survivor> findAll() {
        return survivorRepository.findAll();
    }

    @Override
    public Set<Survivor> findByNationality(String nationality) {
        return survivorRepository.findByNationality(nationality);
    }

    @Override
    public Set<Survivor> findByRating(String rating) {
        int intRating = Integer.parseInt(rating);
        return survivorRepository.findByRating(intRating);
    }

    @Override
    public Set<Survivor> findByreleaseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);
        return survivorRepository.findByReleaseDate(localDate);
    }

    @Override
    public Survivor findByName(String name) {
        return survivorRepository.findByName(name);
    }
}
