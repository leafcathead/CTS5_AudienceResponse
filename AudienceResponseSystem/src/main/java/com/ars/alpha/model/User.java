package com.ars.alpha.model;

import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
// @Table(name = "User")
public class User {

    private UUID id;
    private String name;
    private UUID session;

    public User() {

    }

}
