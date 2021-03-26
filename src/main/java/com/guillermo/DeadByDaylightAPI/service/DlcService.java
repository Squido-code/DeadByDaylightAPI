package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;


import java.util.Set;

public interface DlcService {
    Set<Dlc> findAll();
    Dlc findByName(String name);
}
