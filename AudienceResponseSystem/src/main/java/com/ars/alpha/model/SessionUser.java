package com.ars.alpha.model;


import javax.persistence.*;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
// @Table(name = "User")
@Entity
@Table(name = "SessionUser")
@NamedStoredProcedureQuery(name = "UPDATE_DISPLAY_NAME", procedureName = "UPDATE_DISPLAY_NAME", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "userID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "newName", type = String.class) })
public class SessionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
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

    public String getDisplayName() { return this.name; }

    public void setDisplayName (String newName) {
        this.name = newName;
    }

    public SessionUser () {

    }

    public SessionUser(Long id) {
        this.id = id;
    }

    public SessionUser(Long id, String name) {
        this.id = id; this.name = name;
    }

    public SessionUser(Long id, Long sessionID) {
        this.id = id;
        this.session = new SessionRoom(sessionID);
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", session=" + session.toString() +
                '}';
    }
}
