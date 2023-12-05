package de.ostfalia.group4.controllers;

import de.ostfalia.group4.domain.repositories.StatistikRepository;
import de.ostfalia.group4.domain.repositories.UserRepository;
import de.ostfalia.group4.models.Statistik;
import de.ostfalia.group4.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatistikRepository repository;
    @GetMapping

    public ResponseEntity<List<Statistik>> statistikAbfragen() {
        List<Statistik> statistikList=repository.findAll();
        return new ResponseEntity<>(statistikList, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> statistikHinzufügen(@RequestBody Statistik statistik) {
        try {
            User user = userRepository.findByName("gobbi");
            statistik.setUser(user);
            repository.save(statistik);
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
