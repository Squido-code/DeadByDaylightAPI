package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.service.PerkService;
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

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Slf4j
@Tag(name = "Perks", description = "List of survivors abilities")
@RestController
public class PerkController {

    @Autowired
    private PerkService perkService;
    @Operation(summary = "Get a list of Perks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of perks",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Perk.class)))),
    })
    @GetMapping("perks")
    public ResponseEntity<Set<Perk>> getPerks(@RequestParam(value ="exhaustion", required = false)Boolean isExhaustion,
                                              @RequestParam(required = false) String versionNumber,
                                              @RequestParam(value = "survivor",required = false) String survivorName){
        log.info("initizalized getPerks");
        Set<Perk> perkSet = null;
        boolean exhaustionFilter =false;
        boolean versionFilter = false;
        boolean survivorFilter = false;

        if(isAllempty(isExhaustion,versionNumber,survivorName)){
            log.info("initialized findAll");
            perkSet = perkService.findAll();

        }
        if(isExhaustion != null){
            log.info("initizaliced findByexhaustion ");
            perkSet = perkService.findByExhaustion(isExhaustion);
            exhaustionFilter=true;
        }
        if(versionNumber!= null){
            log.info("initialized findByVersionNumber");
            if(perkSet != null){
                Iterator<Perk> iterator = perkService.
                        findByVersionNumber(versionNumber).
                        iterator();
                while(iterator.hasNext()){
                    perkSet.add(iterator.next());
                }
            }else{
                perkSet= perkService.findByVersionNumber(versionNumber);
            }
            versionFilter = true;
        }
        if(survivorName != null){
            log.info("initialized findBySurvivor");
            if(perkSet != null){
                Iterator<Perk> iterator = perkService.
                        findBySurvivor(survivorName).
                        iterator();
                while(iterator.hasNext()){
                    perkSet.add(iterator.next());
                }
            }else{
                perkSet = perkService.findBySurvivor(survivorName);
            }
            survivorFilter = true;
        }
        //filters
        if(exhaustionFilter){
            Stream<Perk> perkStream = perkSet.stream();
            perkSet = perkStream
                    .filter(perk -> perk.getExhaustion() == isExhaustion)
                    .collect(Collectors.toSet());
        }
        if(versionFilter){
            Stream<Perk> perkStream = perkSet.stream();
            perkSet = perkStream
                    .filter(perk -> perk.getVersionNumber().equals(versionNumber))
                    .collect(Collectors.toSet());
        }
        if(survivorFilter){
            Stream<Perk> perkStream =perkSet.stream();
            perkSet = perkStream
                    .filter(perk -> perk.getSurvivor().getName().equals(survivorName))
                    .collect(Collectors.toSet());
        }
        log.info("finished getPerks");
        return new ResponseEntity<>(perkSet, HttpStatus.OK);

    }
    @Operation(summary = "Get an specific perk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perk found", content = @Content(schema = @Schema(implementation = Perk.class))),
            @ApiResponse(responseCode = "204", description = "The PErk does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/perks/{id}")
    public ResponseEntity<Perk> getPerk(@PathVariable long id) {
        log.info("init getPerk");
        Perk perk;
        try {
            perk = perkService.findById(id);
        } catch (NotFoundException ex) {
            log.error("perk not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(perk, HttpStatus.OK);
    }
    @Operation(summary = "Register a new Perk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perk registered", content = @Content(schema = @Schema(implementation = Perk.class)))
    })
    @PostMapping("/addPerk")
    public ResponseEntity<Perk> addPerk(@RequestBody Perk perk) {
        log.info("init addPerk");
        Perk addPerk = perkService.addPerk(perk);
        return new ResponseEntity<>(addPerk, HttpStatus.CREATED);
    }
    @Operation(summary = "Delete a specific perk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perk deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "204", description = "Perk not found", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/perks/{id}")
    public ResponseEntity<Response> deleteSurvivor(@PathVariable long id) {
        log.info("init deletePerk");
        try{
            perkService.deletedById(id);
        }catch (NotFoundException ex){
            log.error("survivor not found");
            return new ResponseEntity<>(Response.errorResponse(Response.NOT_FOUND,"perk not found"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @Operation(summary = "Modify a specific perk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perk modified", content = @Content(schema = @Schema(implementation = Perk.class))),
            @ApiResponse(responseCode = "204", description = "The Perk does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/perks/{id}")
    public ResponseEntity<Perk> modifyPerk(@PathVariable long id, @RequestBody Perk newPerk) {
        log.info("init modifyPerk");
        Perk perk;
        try{
            perk = perkService.modifyPerk(id, newPerk);
        }catch (NotFoundException ex){
            log.error("Perk not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(perk, HttpStatus.OK);
    }
    private Boolean isAllempty(Boolean isExhaustion,String versionNumber, String survivorName){
        log.info("init isAllempty");
        return isExhaustion == null && versionNumber == null && survivorName == null;
    }
}
