package com.ars.alpha.other;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Password {

    private String password;


    /**
     * Constructor for Password
     * @param randomPassword randomPassword for Session
     */
    public Password(@JsonProperty("password") String randomPassword) {
        this.password = randomPassword;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
