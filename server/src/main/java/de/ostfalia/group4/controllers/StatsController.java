package de.ostfalia.group4.controllers;

import de.ostfalia.group4.repositories.StatistikRepository;
import de.ostfalia.group4.repositories.UserRepository;
import de.ostfalia.group4.models.Statistik;
import de.ostfalia.group4.models.User;
import io.fusionauth.jwt.InvalidJWTException;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.JWTVerifierException;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller für gamestats API
 */
@RestController
@RequestMapping("/api/stats")
public class StatsController {
    /**
     * Benutzer Datenbankanbindung
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Statistik Datenbankanbindung
     */
    @Autowired
    private StatistikRepository repository;
    /**
     * Secret-Key der Anwendung für JWTs
     */
    @Value("${application.secret}")
    private String applicationSecret;

    /**
     * Endpunkt um statistiken des Nutzers zu liefern
     *
     * @param authorization JWT als Authorization header
     * @return alle Statistiken als JSON
     */
    @GetMapping
    public ResponseEntity<List<Statistik>> statistikAbfragen(@RequestHeader("Authorization") String authorization) {
        JWT jwt;
        try {
            // JWT wird als String gespeichert
            String encodedJWT = authorization.split(" ")[1];

            // Build an HMC verifier using the same secret that was used to sign the JWT
            Verifier verifier = HMACVerifier.newVerifier(applicationSecret);

            // Verify and decode the encoded string JWT to a rich object
            jwt = JWT.getDecoder().decode(encodedJWT, verifier);
        }
        // Fehlerhandling für JWT (unkorrekte Formatierung des headers, Token Signatur ungültig, Token expired, Token falsch formatiert)
        catch (ArrayIndexOutOfBoundsException | JWTVerifierException | JWTExpiredException | InvalidJWTException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String nutzer = (String) jwt.getAllClaims().get("name");
        // Statistik für den Nutzer wird im Repository gesucht und als Liste erstellt und ausgegeben.
        User user = userRepository.findByName(nutzer);
        List<Statistik> statistikList = repository.findAllByUser(user);
        return new ResponseEntity<>(statistikList, HttpStatus.OK);
    }

    /**
     * Eine Statistik in die Datenbank hinzufügen
     *
     * @param statistik die Statistik als JSON-Body
     * @param authorization JWT als Authorization header
     * @return Ob die Statistik hinzugefügt wurde (Code 201), oder nicht (Code 500)
     */
    @PostMapping(path = "/add")
    public ResponseEntity<String> statistikHinzufügen(@RequestBody Statistik statistik, @RequestHeader("Authorization") String authorization) {
        JWT jwt;
        try {
            // JWT wird als String gespeichert
            String encodedJWT = authorization.split(" ")[1];

            // Build an HMC verifier using the same secret that was used to sign the JWT
            Verifier verifier = HMACVerifier.newVerifier(applicationSecret);

            // Verify and decode the encoded string JWT to a rich object
            jwt = JWT.getDecoder().decode(encodedJWT, verifier);
        }
        // Fehlerhandling für JWT (unkorrekte Formatierung des headers, Token Signatur ungültig, Token expired, Token falsch formatiert)
        catch (ArrayIndexOutOfBoundsException | JWTVerifierException | JWTExpiredException | InvalidJWTException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String nutzer = (String) jwt.getAllClaims().get("name");
        //Erstellt im Repository eine neue Statistik für den user.
        try {
            User user = userRepository.findByName(nutzer);
            statistik.setUser(user);
            repository.save(statistik);
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
