package de.ostfalia.group4.models;

public class AuthResponse {
    private String jwt;
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
