package org.udem.ift2255.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestDTO {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
