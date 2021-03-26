package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.service.SurvivorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SurvivorController {
    @Autowired
    private SurvivorService survivorService;

    @GetMapping("survivors")
    public ResponseEntity<Set<Survivor>> getSurvivors(@RequestParam(value = "name", defaultValue = "")String name){
        Set<Survivor> survivorSet = null;
        if(name.equals("")){
            survivorSet = survivorService.findAll();
        }else{
            //TODO
        }
        return new ResponseEntity<>(survivorSet, HttpStatus.OK);
    }
}
