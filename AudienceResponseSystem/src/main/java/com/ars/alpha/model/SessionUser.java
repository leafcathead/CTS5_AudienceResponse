package com.ars.alpha.model;

import javax.persistence.*;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
// @Table(name = "User")
@Entity
@Table(name = "SessionUser")
public class SessionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DisplayName")
    private String name;

    @ManyToOne
    @JoinColumn(name = "SessionID")
    SessionRoom session;

    public SessionRoom getSession() {
        return session;
    }

    public void setSession(SessionRoom session) {
        this.session = session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessionUser() {

    }

}
