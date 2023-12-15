package de.ostfalia.group4.controllers;

import de.ostfalia.group4.domain.repositories.UserRepository;
import de.ostfalia.group4.models.User;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Value("${application.secret}")
    private String applicationSecret;

    @GetMapping(path = "/login")
    public ResponseEntity<String> authentifizieren(@RequestParam String benutzername, @RequestParam String passwort) {
        User user = userRepository.findByNameAndPassword(benutzername, passwort);
        if (user != null) {
            // Build an HMAC signer using a SHA-256 hash
            Signer signer = HMACSigner.newSHA256Signer(applicationSecret);

            // Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
            JWT jwt = new JWT().setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                    .addClaim("name", user.getName())
                    .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(180));

            // Sign and encode the JWT to a JSON string representation
            String encodedJWT = JWT.getEncoder().encode(jwt, signer);
            return new ResponseEntity<>(encodedJWT, HttpStatus.OK);
        }
        return new ResponseEntity<>("Falsches Passwort", HttpStatus.FORBIDDEN);

    }

    @DeleteMapping(path = "/logout")
    public void ausloggen() {

    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> registrieren(@RequestBody User user) {
        if (userRepository.findByName(user.getName()) != null) {
            return new ResponseEntity<>("Nutzer bereits vorhanden", HttpStatus.CONFLICT);
        }
        //if (user.getPassword().isEmpty()) {
        //    return new ResponseEntity<>("Bitte Passwort eingeben", HttpStatus.BAD_REQUEST);
        //}
        userRepository.save(user);
        // Prozess zum erstellen des JWT
        Signer signer = HMACSigner.newSHA256Signer(applicationSecret);

        JWT jwt = new JWT().setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .addClaim("name", user.getName())
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(180));

        // Signatur JWT
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        return new ResponseEntity<>(encodedJWT, HttpStatus.CREATED);

    }
}
