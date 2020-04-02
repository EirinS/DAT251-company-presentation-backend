package com.presentation.util;

public class AuthenticationRequest {
    private int id;
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
