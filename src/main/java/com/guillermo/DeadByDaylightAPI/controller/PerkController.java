package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.service.PerkService;
import com.guillermo.DeadByDaylightAPI.support.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PerkController {
    private final Logger logger = LoggerFactory.getLogger(PerkController.class);
    @Autowired
    private PerkService perkService;

    @GetMapping("perks")
    public ResponseEntity<Set<Perk>> getPerks(@RequestParam(value ="exhaustion", required = false)Boolean isExhaustion,
                                              @RequestParam(required = false) String versionNumber,
                                              @RequestParam(value = "survivor",required = false) String survivorName){
        logger.info("initizalized getPerks");
        Set<Perk> perkSet = null;
        boolean exhaustionFilter =false;
        boolean versionFilter = false;
        boolean survivorFilter = false;

        if(isAllempty(isExhaustion,versionNumber,survivorName)){
            logger.info("initialized findAll");
            perkSet = perkService.findAll();

        }
        if(isExhaustion != null){
            logger.info("initizaliced findByexhaustion ");
            perkSet = perkService.findByExhaustion(isExhaustion);
            exhaustionFilter=true;
        }
        if(versionNumber!= null){
            logger.info("initialized findByVersionNumber");
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
            logger.info("initialized findBySurvivor");
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
        logger.info("finished getPerks");
        return new ResponseEntity<>(perkSet, HttpStatus.OK);

    }
    @GetMapping("/perks/{id}")
    public ResponseEntity<Perk> getPerk(@PathVariable long id) {
        logger.info("init getPerk");
        Perk perk;
        try {
            perk = perkService.findById(id);
        } catch (NotFoundException ex) {
            logger.error("perk not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perk, HttpStatus.OK);
    }

    @PostMapping("/addPerk")
    public ResponseEntity<Perk> addPerk(@RequestBody Perk perk) {
        logger.info("init addPerk");
        Perk addPerk = perkService.addPerk(perk);
        return new ResponseEntity<>(addPerk, HttpStatus.OK);
    }

    @DeleteMapping("/perks/{id}")
    public ResponseEntity<Response> deleteSurvivor(@PathVariable long id) {
        logger.info("init deletePerk");
        try{
            perkService.deletedById(id);
        }catch (NotFoundException ex){
            logger.error("survivor not found");
            return new ResponseEntity<>(Response.errorResponse(Response.NOT_FOUND,"perk not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @PutMapping("/perks/{id}")
    public ResponseEntity<Perk> modifyPerk(@PathVariable long id, @RequestBody Perk newPerk) {
        logger.info("init modifyPerk");
        Perk perk;
        try{
            perk = perkService.modifyPerk(id, newPerk);
        }catch (NotFoundException ex){
            logger.error("Perk not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(perk, HttpStatus.OK);
    }
    private Boolean isAllempty(Boolean isExhaustion,String versionNumber, String survivorName){
        logger.info("init isAllempty");
        return isExhaustion == null && versionNumber == null && survivorName == null;
    }
}
