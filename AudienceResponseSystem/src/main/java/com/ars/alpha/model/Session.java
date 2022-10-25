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

    @Column(name = "Owner", nullable = false)
    private Long owner;

    @Column(name = "Timestamp", nullable = false) // This line might have to be removed? Can we do this just in Table definition?
    private Timestamp timestamp;


    public Session() {

    }

    public Long getID() {
        return ID;
    }

    public Long getOwner() {
        return owner;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }



}
