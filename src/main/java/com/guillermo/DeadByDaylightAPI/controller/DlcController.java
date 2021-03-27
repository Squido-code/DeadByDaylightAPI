package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.service.DlcService;
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
public class DlcController {
    private final Logger logger = LoggerFactory.getLogger(DlcController.class);
    @Autowired
    private DlcService dlcService;

    @GetMapping("dlc")
    public ResponseEntity<Set<Dlc>> getKillers(@RequestParam (required = false) String chapter,
                                                  @RequestParam (required = false) String releaseDate,
                                                  @RequestParam (required = false) String price){
        Set<Dlc> dlcSet = null;
        Boolean chapterfilter = false;
        Boolean dateFilter = false;
        Boolean priceFilter = false;
        //all dlc
        if(isAllEmpty(chapter,releaseDate,price)){
            logger.info("init findAll");
            dlcSet = dlcService.findAll();
        }
        //parameters
        if(chapter != null){
            logger.info("init findByChapter");
            dlcSet = dlcService.findByChapter(chapter);
            chapterfilter=true;
        }

        if(releaseDate != null) {
            logger.info("init findByReleaseDate");
            if (dlcSet != null) {
                Iterator<Dlc> iterator = dlcService
                        .findByReleaseDate(releaseDate)
                        .iterator();
                while (iterator.hasNext()) {
                    dlcSet.add(iterator.next());
                }
            } else {
                dlcSet = dlcService.findByReleaseDate(releaseDate);
            }
            dateFilter = true;
        }

        if(price != null){
            logger.info("init findByPrice");
            if(dlcSet != null){
                Iterator<Dlc> iterator = dlcService
                        .findByPrice(price)
                        .iterator();
                while (iterator.hasNext()) {
                    dlcSet.add(iterator.next());
                }
            }else{
                dlcSet = dlcService.findByPrice(price);
            }
            priceFilter = true;
        }
        //filters
        if(chapterfilter){
            logger.info("init chapterFilter");
            Stream<Dlc> dlcStream = dlcSet.stream();
            dlcSet = dlcStream
                    .filter(dlc -> dlc.getChapterNumber() == Integer.parseInt(chapter))
                    .collect(Collectors.toSet());
        }
        if(dateFilter){
            logger.info("init dateFilter");
            Stream<Dlc> dlcStream = dlcSet.stream();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(releaseDate,formatter);
            dlcSet = dlcStream
                    .filter(dlc -> dlc.getReleaseDate().equals(localDate))
                    .collect(Collectors.toSet());
        }
        if(priceFilter){
            logger.info("init priceFilter");
            Stream<Dlc> dlcStream = dlcSet.stream();
            double doublePrice = Double.parseDouble(price);
            dlcSet = dlcStream
                    .filter(dlc -> dlc.getPrice() == doublePrice)
                    .collect(Collectors.toSet());
        }
        logger.info("finished getDLC");
        return new ResponseEntity<>(dlcSet, HttpStatus.OK);
    }
    private Boolean isAllEmpty(String chapter,String releaseDate,String price){
        logger.info("init isAllEmpty");
        if(chapter == null && releaseDate == null && price == null){
            return true;
        }
        return false;
    }

}
