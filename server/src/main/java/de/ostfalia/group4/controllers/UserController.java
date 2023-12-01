package de.ostfalia.group4.controllers;

import de.ostfalia.group4.domain.repositories.UserRepository;
import de.ostfalia.group4.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping(path="/login")
    public ResponseEntity<String> authentifizieren(@RequestParam String benutzername, @RequestParam String passwort) {
        User user=userRepository.findByNameAndPassword(benutzername, passwort);
        if(user!=null){
            return new ResponseEntity<>("Hallo Adolf", HttpStatus.OK);
        }
        return new ResponseEntity<>("Falsches Passwort", HttpStatus.FORBIDDEN);

    }
    @DeleteMapping(path="/logout")
    public void ausloggen() {

    }
    @GetMapping (path="/register")
    public ResponseEntity<String> registrieren(@RequestParam String benutzername, @RequestParam String passwort) {
        if (userRepository.findByName(benutzername)!=null){
            return new ResponseEntity<>("Nutzer bereits vorhanden", HttpStatus.CONFLICT);
        }
        User user=new User(benutzername, passwort);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
