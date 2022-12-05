package com.ars.alpha.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BugReport")

public class BugReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Category")
    private String category;

    @Column(name = "BugText")
    private String bugText;

    @Column(name = "Status")
    private BugStatus status;

    @Column(name = "Timestamp")
    private Timestamp timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBugText() {
        return bugText;
    }

    public void setBugText(String bugText) {
        this.bugText = bugText;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public enum BugStatus {

        UNRESOLVED,
        REPLICATED,
        RESOLVED

    }


}
