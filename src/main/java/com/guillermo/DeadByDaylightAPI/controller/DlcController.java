package com.guillermo.DeadByDaylightAPI.controller;

import com.guillermo.DeadByDaylightAPI.domain.Dlc;
import com.guillermo.DeadByDaylightAPI.domain.Killer;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.service.DlcService;
import com.guillermo.DeadByDaylightAPI.support.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "DLCs", description = "List of published DLCs")
@RestController
public class DlcController {
    private final Logger logger = LoggerFactory.getLogger(DlcController.class);
    @Autowired
    private DlcService dlcService;
    @Operation(summary = "Get a list of DLCs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of DLCs",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Dlc.class)))),
    })
    @GetMapping("dlcs")
    public ResponseEntity<Set<Dlc>> getKillers(@RequestParam (required = false) String chapter,
                                                  @RequestParam (required = false) String releaseDate,
                                                  @RequestParam (required = false) String price){
        Set<Dlc> dlcSet = null;
        boolean chapterfilter = false;
        boolean dateFilter = false;
        boolean priceFilter = false;
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
    @Operation(summary = "Get an specific DLC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DLC found", content = @Content(schema = @Schema(implementation = Dlc.class))),
            @ApiResponse(responseCode = "404", description = "The DLC does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/dlcs/{id}")
    public ResponseEntity<Dlc> getDlc(@PathVariable long id) {
        logger.info("init getDlc");
        Dlc dlc;
        try {
            dlc = dlcService.findById(id);
        } catch (NotFoundException ex) {
            logger.error("dlc not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dlc, HttpStatus.OK);
    }
    @Operation(summary = "Register a new DLC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DLC registered", content = @Content(schema = @Schema(implementation = Dlc.class)))
    })
    @PostMapping("/addDlc")
    public ResponseEntity<Dlc> addDlc(@RequestBody Dlc dlc) {
        logger.info("init addDlc");
        Dlc addedDlc = dlcService.addDlc(dlc);
        return new ResponseEntity<>(addedDlc, HttpStatus.OK);
    }
    @Operation(summary = "Delete a DLC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DLC deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "DLC not found", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/dlcs/{id}")
    public ResponseEntity<Response> deleteSurvivor(@PathVariable long id) {
        logger.info("init deleteDlc");
        try{
            dlcService.deletedById(id);
        }catch (NotFoundException ex){
            logger.error("Dlc not found");
            return new ResponseEntity<>(Response.errorResponse(Response.NOT_FOUND,"dlc not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @Operation(summary = "Modify a DLC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DLC modified", content = @Content(schema = @Schema(implementation = Dlc.class))),
            @ApiResponse(responseCode = "404", description = "The DLC does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/dlcs/{id}")
    public ResponseEntity<Dlc> modifySurvivor(@PathVariable long id, @RequestBody Dlc newDlc) {
        logger.info("init modifySurvivor");
        Dlc dlc;
        try{
            dlc = dlcService.modifyDlc(id, newDlc);
        }catch (NotFoundException ex){
            logger.error("Dlc not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dlc, HttpStatus.OK);
    }
    private Boolean isAllEmpty(String chapter,String releaseDate,String price){
        logger.info("init isAllEmpty");
        return chapter == null && releaseDate == null && price == null;
    }

}
