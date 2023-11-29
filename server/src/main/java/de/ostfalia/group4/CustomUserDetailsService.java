package de.ostfalia.group4;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Lade Benutzerinformationen aus der Datenbank oder einem anderen Speicherort
        // Hier wird ein Dummy-Benutzer mit festen Werten erstellt
        return User.withUsername("username")
                .password("{noop}password") // {noop} bedeutet, dass kein Passwortencoder verwendet wird
                .roles("USER")
                .build();
    }
}
