package com.ars.alpha.model;


import javax.persistence.*;
import java.sql.Timestamp;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
@Entity
@Table(name = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;


    @Column()
    private String sessionJoinID;

    @OneToOne
    @JoinColumn(name = "OwnerID", nullable = false)
    private SessionUser owner;

    @Column(name = "Timestamp", nullable = false) // This line might have to be removed? Can we do this just in Table definition?
    private Timestamp timestamp;

    public void setOwner(SessionUser owner) {
        this.owner = owner;
    }


    public Session() {

    }

    public Long getID() {
        return id;
    }

    public SessionUser getOwner() {
        return owner;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setID(Long ID) {
        this.id = ID;
    }



}
