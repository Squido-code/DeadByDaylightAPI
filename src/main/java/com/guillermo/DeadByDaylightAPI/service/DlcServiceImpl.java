package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class DlcServiceImpl implements DlcService{
    @Autowired
    private DlcService dlcService;
    @Override
    public Set<Dlc> findAll() {
        return dlcService.findAll();
    }

    @Override
    public Dlc findByName(String name) {
        return dlcService.findByName(name);
    }
}
