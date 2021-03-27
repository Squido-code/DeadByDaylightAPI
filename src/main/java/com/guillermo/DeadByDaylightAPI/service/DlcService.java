package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;


import java.time.LocalDate;
import java.util.Set;

public interface DlcService {
    Set<Dlc> findAll();
    Set<Dlc> findByChapter(String chapter);
    Set<Dlc> findByReleaseDate(String date);
    Set<Dlc> findByPrice(String price);
    Dlc findByName(String name);
    Dlc findById(long id);

    Dlc addDlc(Dlc dlc);
    void deletedById(long id);
    Dlc modifyDlc(long id, Dlc newDlc);
}
