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
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return new ResponseEntity<>("Falsches Passwort", HttpStatus.FORBIDDEN);

    }
    @DeleteMapping(path="/logout")
    public void ausloggen() {

    }
    @PostMapping (path="/register")
    public ResponseEntity<String> registrieren(@RequestBody User user) {
        if (userRepository.findByName(user.getName())!=null){
            return new ResponseEntity<>("Nutzer bereits vorhanden", HttpStatus.CONFLICT);
        }
        if (user.getPassword().isEmpty()) {
            return new ResponseEntity<>("Bitte Passwort eingeben", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
