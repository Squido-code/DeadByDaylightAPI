package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.service.KillerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class KillerController {
    private final Logger logger = LoggerFactory.getLogger(KillerController.class);
    @Autowired
    private KillerService killerService;

    @GetMapping("killers")
    public ResponseEntity<Set<Killer>> getKillers(@RequestParam(required = false)String difficulty,
                                                  @RequestParam(required = false)String power,
                                                  @RequestParam(required = false)String releaseDate){
        Set<Killer> killerSet = null;
        Boolean dificultyFilter = false;
        Boolean powerFilter = false;
        Boolean dateFilter = false;
        //allKiller
        if(isAllEmpty(difficulty,power,releaseDate)){
            logger.info("init findAll");
            killerSet = killerService.findAll();
        }
        //parameters
        if(difficulty != null){
            logger.info("init findByDifficult");
            killerSet = killerService.findByDifficulty(difficulty);
            dificultyFilter = true;
        }
        if(power != null){
            logger.info("init findByPower");
            if(killerSet != null){
                Iterator<Killer> iterator = killerService
                        .findByPower(power)
                        .iterator();
                while(iterator.hasNext()){
                    killerSet.add(iterator.next());
                }
            }else{
                killerSet = killerService.findByPower(power);
            }
            powerFilter = true;
        }
        if(releaseDate != null){
            logger.info("init findByReleaseDate");
            if (killerSet != null) {
                Iterator<Killer> iterator = killerService.
                        findByReleaseDate(releaseDate).
                        iterator();
                while (iterator.hasNext()) {
                    killerSet.add(iterator.next());
                }
            } else {
                killerSet = killerService.findByReleaseDate(releaseDate);
            }
            dateFilter= true;
        }
        //filters
        if(dificultyFilter){
            logger.info("init difficulty filter");
            Stream<Killer> killerStream = killerSet.stream();
            killerSet = killerStream
                    .filter(killer -> killer.getDifficulty().equals(difficulty))
                    .collect(Collectors.toSet());
        }
        if(powerFilter){
            logger.info("init powerFilter");
            Stream<Killer> killerStream = killerSet.stream();
            killerSet = killerStream
                    .filter(killer -> killer.getPower().equals(power))
                    .collect(Collectors.toSet());
        }
        if(dateFilter){
            logger.info("init dateFilter");
            Stream<Killer> killerStream = killerSet.stream();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(releaseDate,formatter);
            killerSet = killerStream.filter(killer -> killer.getReleaseDate().equals(localDate)).
                    collect(Collectors.toSet());
        }
        logger.info("finished getKillers");
        return new ResponseEntity<>(killerSet, HttpStatus.OK);
    }
    private Boolean isAllEmpty(String difficulty,String power,String releaseDate){
        logger.info("intit isAllempty");
        if(difficulty == null && power == null && releaseDate==null){
            return true;
        }
        return false;
    }
}
