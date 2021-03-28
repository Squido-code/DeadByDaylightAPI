package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.service.SurvivorService;
import com.guillermo.DeadByDaylightAPI.support.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "Survivors", description = "List of survivors available")
@RestController
public class SurvivorController {


    @Autowired
    private SurvivorService survivorService;
    @Operation(summary = "Get a list of survivors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of survivors",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Survivor.class)))),
    })
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
            log.info("initialized findAll");
            survivorSet = survivorService.findAll();
        }
        //parameters
        if (nationality != null) {
            log.info("initialized findByNationality");
            survivorSet = survivorService.findByNationality(nationality);
            nationalityFilter = true;
        }
        if (rating != null) {
            log.info("initialized findByRating");

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
            log.info("initialized findByReleaseDate");
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
        log.info("finished getSurvivors");
        return new ResponseEntity<>(survivorSet, HttpStatus.OK);

    }
    @Operation(summary = "Get an specific survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Survivor found", content = @Content(schema = @Schema(implementation = Survivor.class))),
            @ApiResponse(responseCode = "204", description = "The survivor does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/survivors/{id}")
    public ResponseEntity<Survivor> getSurvivor(@PathVariable long id) {
        log.info("init getSurvivor");
        Survivor survivor;
        try {
            survivor = survivorService.findById(id);
        } catch (NotFoundException ex) {
            log.error("survivor not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(survivor, HttpStatus.OK);
    }
    @Operation(summary = "Register a new survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Survivor registered", content = @Content(schema = @Schema(implementation = Survivor.class)))
    })
    @PostMapping("/addSurvivor")
    public ResponseEntity<Survivor> addSurvivor(@RequestBody Survivor survivor) {
        log.info("init addSurvivor");
        Survivor addedSurvivor = survivorService.addSurvivor(survivor);
        return new ResponseEntity<>(addedSurvivor, HttpStatus.CREATED);
    }
    @Operation(summary = "Delete a survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Survivor deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "204", description = "Survivor not found", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/survivors/{id}")
    public ResponseEntity<Response> deleteSurvivor(@PathVariable long id) {
        log.info("init deleteSurvivor");
        try{
            survivorService.deletedById(id);
        }catch (NotFoundException ex){
            log.error("survivor not found");
            return new ResponseEntity<>(Response.errorResponse(Response.NOT_FOUND,"survivor not found"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @Operation(summary = "Modify a survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Survivor modified", content = @Content(schema = @Schema(implementation = Survivor.class))),
            @ApiResponse(responseCode = "204", description = "The survivor does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/survivors/{id}")
    public ResponseEntity<Survivor> modifySurvivor(@PathVariable long id, @RequestBody Survivor newSurvivor) {
        log.info("init modifySurvivor");
        Survivor survivor;
        try{
            survivor = survivorService.modifySurvivor(id, newSurvivor);
        }catch (NotFoundException ex){
            log.error("survivor not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(survivor, HttpStatus.OK);
    }
    private Boolean isAllEmpty(String nationality, String rating, String releaseDate) {
        return nationality == null && rating == null && releaseDate == null;
    }
}
