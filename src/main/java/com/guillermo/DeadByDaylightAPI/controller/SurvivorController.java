package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.service.SurvivorService;
import com.guillermo.DeadByDaylightAPI.support.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Set<Survivor>> getSurvivors(@RequestParam(required = false) String nationality,
                                                      @RequestParam(required = false) String rating,
                                                      @RequestParam(value = "date", required = false) String releaseDate) {
        Set<Survivor> survivorSet = null;
        boolean nationalityFilter = false;
        boolean ratingFilter = false;
        boolean dateFilter = false;

        //all survivors
        if (isAllEmpty(nationality, rating, releaseDate)) {
            logger.info("initialized findAll");
            survivorSet = survivorService.findAll();
        }
        //parameters
        if (nationality != null) {
            logger.info("initialized findByNationality");
            survivorSet = survivorService.findByNationality(nationality);
            nationalityFilter = true;
        }
        if (rating != null) {
            logger.info("initialized findByRating");

            if (survivorSet != null) {
                Iterator<Survivor> iterator = survivorService.
                        findByRating(rating).
                        iterator();
                while (iterator.hasNext()) {
                    survivorSet.add(iterator.next());
                }
            } else {
                survivorSet = survivorService.findByRating(rating);
            }
            ratingFilter = true;
        }
        if (releaseDate != null) {
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
            dateFilter = true;
        }
        //filter
        if (nationalityFilter) {
            Stream<Survivor> survivorStream = survivorSet.stream();
            survivorSet = survivorStream
                    .filter(survivor -> survivor.getNationality().equals(nationality))
                    .collect(Collectors.toSet());
        }
        if (ratingFilter) {
            Stream<Survivor> survivorStream = survivorSet.stream();
            survivorSet = survivorStream
                    .filter(survivor -> survivor.getRating() == Integer.parseInt(rating))
                    .collect(Collectors.toSet());
        }
        if (dateFilter) {
            Stream<Survivor> survivorStream = survivorSet.stream();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(releaseDate, formatter);
            survivorSet = survivorStream.filter(survivor -> survivor.getReleaseDate().equals(localDate)).
                    collect(Collectors.toSet());
        }
        logger.info("finished getSurvivors");
        return new ResponseEntity<>(survivorSet, HttpStatus.OK);

    }

    @GetMapping("/survivors/{id}")
    public ResponseEntity<Survivor> getSurvivor(@PathVariable long id) {
        logger.info("init getSurvivor");
        Survivor survivor;
        try {
            survivor = survivorService.findById(id);
        } catch (NotFoundException ex) {
            logger.error("survivor not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(survivor, HttpStatus.OK);
    }

    @PostMapping("/addSurvivor")
    public ResponseEntity<Survivor> addSurvivor(@RequestBody Survivor survivor) {
        logger.info("init addSurvivor");
        Survivor addedSurvivor = survivorService.addSurvivor(survivor);
        return new ResponseEntity<>(addedSurvivor, HttpStatus.OK);
    }

    @DeleteMapping("/survivors/{id}")
    public ResponseEntity<Response> deleteSurvivor(@PathVariable long id) {
        logger.info("init deleteSurvivor");
        try{
            survivorService.deletedById(id);
        }catch (NotFoundException ex){
            logger.error("survivor not found");
            return new ResponseEntity<>(Response.errorResponse(Response.NOT_FOUND,"survivor not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @PutMapping("/survivors/{id}")
    public ResponseEntity<Survivor> modifySurvivor(@PathVariable long id, @RequestBody Survivor newSurvivor) {
        logger.info("init modifySurvivor");
        Survivor survivor;
        try{
            survivor = survivorService.modifySurvivor(id, newSurvivor);
        }catch (NotFoundException ex){
            logger.error("survivor not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(survivor, HttpStatus.OK);
    }
    private Boolean isAllEmpty(String nationality, String rating, String releaseDate) {
        return nationality == null && rating == null && releaseDate == null;
    }
}
