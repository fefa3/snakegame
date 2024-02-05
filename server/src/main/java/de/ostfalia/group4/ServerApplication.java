package de.ostfalia.group4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start-punkt der Server-Anwendung
 */
@SpringBootApplication
public class ServerApplication {

    /**
     * Java Main methode
     *
     * @param args Start-Argumente
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
