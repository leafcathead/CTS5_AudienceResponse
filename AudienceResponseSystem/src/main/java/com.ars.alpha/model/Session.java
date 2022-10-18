package com.ars.alpha.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
// @Table(name = "Session")
public class Session {

    private UUID id;
    private UUID owner;
    private Timestamp timestamp;

    public Session() {

    }
}
