package com.ars.alpha.model;

import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(appliesTo = "PanicResponse")
public class PanicResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PanicType")
    private PanicButton panicType;
    @ManyToOne
    @JoinColumn(name = "Panicker")
    private SessionUser panicker;
    @ManyToOne
    @JoinColumn(name = "SessionRoom")
    private SessionRoom session;

    @Column(name = "LogTime")
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public SessionRoom getSession() {
        return session;
    }

    public void setSession(SessionRoom session) {
        this.session = session;
    }

    public SessionUser getPanicker() {
        return panicker;
    }

    public void setPanicker(SessionUser panicker) {
        this.panicker = panicker;
    }

    public PanicButton getPanicType() {
        return panicType;
    }

    public void setPanicType(PanicButton panicType) {
        this.panicType = panicType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
