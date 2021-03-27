package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import com.guillermo.DeadByDaylightAPI.repository.DlcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Service
public class DlcServiceImpl implements DlcService{
    @Autowired
    private DlcRepository dlcRepository;
    @Override
    public Set<Dlc> findAll() {
        return dlcRepository.findAll();
    }

    @Override
    public Set<Dlc> findByChapter(String chapter) {
        int doubleChapter = Integer.parseInt(chapter);
        return dlcRepository.findBychapterNumber(doubleChapter);
    }

    @Override
    public Set<Dlc> findByReleaseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);
        return dlcRepository.findByReleaseDate(localDate);
    }

    @Override
    public Set<Dlc> findByPrice(String price) {
        double doublePrice = Double.parseDouble(price);
        return dlcRepository.findByPrice(doublePrice);
    }

    @Override
    public Dlc findByName(String name) {
        return dlcRepository.findByName(name);
    }
}
