package com.ars.alpha.model;

import javax.persistence.*;
import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
// @Table(name = "User")
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DisplayName")
    private String name;

    @ManyToOne
    @JoinColumn(name = "SessionID")
    Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {

    }

}
