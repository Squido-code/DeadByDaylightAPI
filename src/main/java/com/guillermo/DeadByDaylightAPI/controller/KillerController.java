package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.service.KillerService;
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
@Tag(name = "Killers", description = "List of killers available")
@RestController
public class KillerController {

    @Autowired
    private KillerService killerService;
    @Operation(summary = "Get a list of Killers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of killers",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Killer.class)))),
    })
    @GetMapping("killers")
    public ResponseEntity<Set<Killer>> getKillers(@RequestParam(required = false) String difficulty,
                                                  @RequestParam(required = false) String power,
                                                  @RequestParam(required = false) String releaseDate) {
        Set<Killer> killerSet = null;
        boolean dificultyFilter = false;
        boolean powerFilter = false;
        boolean dateFilter = false;
        //allKiller
        if (isAllEmpty(difficulty, power, releaseDate)) {
            log.info("init findAll");
            killerSet = killerService.findAll();
        }
        //parameters
        if (difficulty != null) {
            log.info("init findByDifficult");
            killerSet = killerService.findByDifficulty(difficulty);
            dificultyFilter = true;
        }
        if (power != null) {
            log.info("init findByPower");
            if (killerSet != null) {
                Iterator<Killer> iterator = killerService
                        .findByPower(power)
                        .iterator();
                while (iterator.hasNext()) {
                    killerSet.add(iterator.next());
                }
            } else {
                killerSet = killerService.findByPower(power);
            }
            powerFilter = true;
        }
        if (releaseDate != null) {
            log.info("init findByReleaseDate");
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
            dateFilter = true;
        }
        //filters
        if (dificultyFilter) {
            log.info("init difficulty filter");
            Stream<Killer> killerStream = killerSet.stream();
            killerSet = killerStream
                    .filter(killer -> killer.getDifficulty().equals(difficulty))
                    .collect(Collectors.toSet());
        }
        if (powerFilter) {
            log.info("init powerFilter");
            Stream<Killer> killerStream = killerSet.stream();
            killerSet = killerStream
                    .filter(killer -> killer.getPower().equals(power))
                    .collect(Collectors.toSet());
        }
        if (dateFilter) {
            log.info("init dateFilter");
            Stream<Killer> killerStream = killerSet.stream();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(releaseDate, formatter);
            killerSet = killerStream.filter(killer -> killer.getReleaseDate().equals(localDate)).
                    collect(Collectors.toSet());
        }
        log.info("finished getKillers");
        return new ResponseEntity<>(killerSet, HttpStatus.OK);
    }
    @Operation(summary = "Get an specific killer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Killer found", content = @Content(schema = @Schema(implementation = Killer.class))),
            @ApiResponse(responseCode = "204", description = "The killer does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/killers/{id}")
    public ResponseEntity<Killer> getKiller(@PathVariable long id) {
        log.info("init getKiller");
        Killer killer;
        try {
            killer = killerService.findById(id);
        } catch (NotFoundException ex) {
            log.error("killer not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(killer, HttpStatus.OK);
    }
    @Operation(summary = "Register a new killer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Killer registered", content = @Content(schema = @Schema(implementation = Killer.class)))
    })
    @PostMapping("/addkiller")
    public ResponseEntity<Killer> addKiller(@RequestBody Killer killer) {
        log.info("init addKiller");
        Killer addedKiller = killerService.addKiller(killer);
        return new ResponseEntity<>(addedKiller, HttpStatus.CREATED);
    }
    @Operation(summary = "Delete a killer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Killer deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "204", description = "Killer not found", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/killers/{id}")
    public ResponseEntity<Response> deleteKiller(@PathVariable long id) {
        log.info("init deleteKiller");
        try {
            killerService.deletedById(id);
        } catch (NotFoundException ex) {
            log.error("killer not found");
            return new ResponseEntity<>(Response.errorResponse(Response.NOT_FOUND, "killer not found"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @Operation(summary = "Modify a killer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Killer modified", content = @Content(schema = @Schema(implementation = Killer.class))),
            @ApiResponse(responseCode = "204", description = "The killer does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/killers/{id}")
    public ResponseEntity<Killer> modifyKiller(@PathVariable long id, @RequestBody Killer newKiller) {
        log.info("init modifyKiller");
        Killer killer;
        try {
            killer = killerService.modifyKiller(id, newKiller);
        } catch (NotFoundException ex) {
            log.error("killer not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(killer, HttpStatus.OK);
    }

    private Boolean isAllEmpty(String difficulty, String power, String releaseDate) {
        log.info("intit isAllempty");
        return difficulty == null && power == null && releaseDate == null;
    }
}
