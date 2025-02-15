package com.websystem.gestioneimpianti.controller;

import com.websystem.gestioneimpianti.model.*;
import com.websystem.gestioneimpianti.service.GestioneImpiantiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
public class GestioneImpiantiController {

    @Autowired
    private GestioneImpiantiService gestioneImpiantiService;

    private static final Logger logger = LoggerFactory.getLogger(GestioneImpiantiController.class);

    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/getImpiantiStatus")
    public List<ImpiantoDto> getImpiantiStatus() {
        return gestioneImpiantiService.getImpiantiStatus();
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/getAllImpianti")
    public ResponseEntity<List<Impianto>> getAllImpianti() {
        List<Impianto> impiantoList = gestioneImpiantiService.getAllImpianti();
        logger.info("result is " + impiantoList);
        if (impiantoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(impiantoList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/getImpianto/{id}")
    public ResponseEntity<ImpiantoPalinsesto> getImpiantoById(@PathVariable Long id) {
        Optional<ImpiantoPalinsesto> optImpiantoP = gestioneImpiantiService.getImpiantoPAttivoById(id);
        logger.info("result is " + (optImpiantoP.isPresent() ? optImpiantoP.get() : "None"));
        return optImpiantoP.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @PutMapping("/updateImpianto/{id}")
    public ResponseEntity<Impianto> updateImpianto(@PathVariable Long id, @RequestBody Impianto updatedImpianto) {
        Optional<Impianto> optionalImpianto = gestioneImpiantiService.getImpiantoById(id);
        if (optionalImpianto.isPresent()) {
            Impianto impianto = optionalImpianto.get();
            System.out.println("impianto: ");
            System.out.println(impianto);
            System.out.println(updatedImpianto);
            if (updatedImpianto.getIdPalinsesto() != null) {
                impianto.setIdPalinsesto(updatedImpianto.getIdPalinsesto());
            }
            impianto.setIsAttivo(updatedImpianto.isAttivo());
            gestioneImpiantiService.saveImpianto(impianto);
            return ResponseEntity.ok(impianto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/getAllPalinsesti")
    public ResponseEntity<List<Palinsesto>> getAllPalinsesti() {
        List<Palinsesto> palinsestoList = gestioneImpiantiService.getAllPalinsesti();
        logger.info("result is " + palinsestoList);
        if (palinsestoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(palinsestoList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/getAllCartelloni")
    public ResponseEntity<List<Cartellone>> getAllCartelloni() {
        List<Cartellone> cartelloniList = gestioneImpiantiService.getAllCartelloni();
        logger.info("result is " + cartelloniList);
        if (cartelloniList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartelloniList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/getImpressioni")
    public ResponseEntity<List<ImpressioniDto>> getImpressioni(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(required = false) Long idImpianto,
            @RequestParam(required = false) Long idCartellone,
            @RequestParam(required = false) Long idPalinsesto) {

        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);

        logger.info("request is [startDate: " + startDate + ", endDate: " + endDate + ", idImpianto: " + idImpianto + ", idCartellone: " + idCartellone + ", idPalinsesto: " + idPalinsesto + "]");
        List<ImpressioniDto> impressioni = gestioneImpiantiService.getImpressioni(startDate, endDate, idImpianto, idCartellone, idPalinsesto);
        if (impressioni.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(impressioni);
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(dateStr, formatter);
    }

}



