package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.service.SurvivorService;
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
public class SurvivorController {
    private final Logger logger = LoggerFactory.getLogger(SurvivorController.class);

    @Autowired
    private SurvivorService survivorService;

    @GetMapping("survivors")
    public ResponseEntity<Set<Survivor>> getSurvivors(@RequestParam(required = false)String nationality,
                                                      @RequestParam(required = false)String rating,
                                                      @RequestParam(value ="date",required = false)String releaseDate){
        Set<Survivor> survivorSet = null;
        Boolean nationalityFilter = false;
        Boolean ratingFilter = false;
        Boolean dateFilter = false;

        //all survivors
        if(isAllEmpty(nationality,rating,releaseDate)){
            logger.info("initialized findAll");
            survivorSet = survivorService.findAll();
        }
        //parameters
        if(nationality != null){
            logger.info("initialized findByNationality");
            survivorSet = survivorService.findByNationality(nationality);
            nationalityFilter = true;
        }
        if(rating != null){
            logger.info("initialized findByRating");

            if(survivorSet != null){
                Iterator<Survivor> iterator = survivorService.
                        findByRating(rating).
                        iterator();
                while(iterator.hasNext()){
                    survivorSet.add(iterator.next());
                }
            }else{
                survivorSet= survivorService.findByRating(rating);
            }
            ratingFilter = true;
        }
        if(releaseDate != null) {
            logger.info("initialized findByReleaseDate");
            if (survivorSet != null) {
                Iterator<Survivor> iterator = survivorService.
                        findByreleaseDate(releaseDate).
                        iterator();
                while (iterator.hasNext()) {
                    survivorSet.add(iterator.next());
                }
            } else {
                survivorSet = survivorService.findByreleaseDate(releaseDate);
            }
            dateFilter= true;
        }
        //filter
        if(nationalityFilter){
            Stream<Survivor> survivorStream = survivorSet.stream();
            survivorSet = survivorStream
                    .filter(survivor -> survivor.getNationality().equals(nationality))
                    .collect(Collectors.toSet());
        }
        if(ratingFilter){
            Stream<Survivor> survivorStream = survivorSet.stream();
            survivorSet = survivorStream
                    .filter(survivor -> survivor.getRating()==Integer.parseInt(rating))
                    .collect(Collectors.toSet());
        }
        if(dateFilter){
            Stream<Survivor> survivorStream = survivorSet.stream();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(releaseDate,formatter);
            survivorSet = survivorStream.filter(survivor -> survivor.getReleaseDate().equals(localDate)).
                        collect(Collectors.toSet());
        }
        logger.info("finished getSurvivors");
        return new ResponseEntity<>(survivorSet, HttpStatus.OK);

    }
    private Boolean isAllEmpty(String nationality, String rating, String releaseDate){
        if(nationality == null && rating == null && releaseDate == null){
            return true;
        }
        return false;
    }
}
