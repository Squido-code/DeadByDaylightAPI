package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;


import java.time.LocalDate;
import java.util.Set;

public interface DlcService {
    Set<Dlc> findAll();
    Set<Dlc> findByChapter(String chapter);
    Set<Dlc> findByReleaseDate(String date);
    Set<Dlc> findByPrice(String price);
    Dlc findByName(String name);
}
