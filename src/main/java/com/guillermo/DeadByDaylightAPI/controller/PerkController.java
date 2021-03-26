package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.service.PerkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

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

        if(isAllempty(isExhaustion,versionNumber,survivorName)){
            logger.info("initialized findAll");
            perkSet = perkService.findAll();
        }
        if(isExhaustion != null){
            logger.info("initizaliced findByexhaustion ");
            perkSet = perkService.findByExhaustion(isExhaustion);
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

        }
        logger.info("finished getPerks");
        return new ResponseEntity<>(perkSet, HttpStatus.OK);

    }
    private Boolean isAllempty(Boolean isExhaustion,String versionNumber, String survivorName){
        if(isExhaustion == null && versionNumber==null && survivorName==null){
            return true;
        }
        return false;
    }
}
