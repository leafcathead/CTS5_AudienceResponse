package com.ars.alpha.model;


import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
@Entity
@Table(name = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long ID;

    private UUID owner;
    private Timestamp timestamp;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Session() {

    }
}
