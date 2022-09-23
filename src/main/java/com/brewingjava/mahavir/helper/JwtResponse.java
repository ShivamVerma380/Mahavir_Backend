package com.brewingjava.mahavir.helper;

import org.springframework.stereotype.Component;

@Component
public class JwtResponse {
 
    private String token;

    private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    @Override
    public String toString() {
        return "JwtResponse [message=" + message + ", token=" + token + "]";
    }

    
}
