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

    /**
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id new ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return bugText
     */
    public String getBugText() {
        return bugText;
    }

    /**
     *
     * @param bugText new bugText
     */
    public void setBugText(String bugText) {
        this.bugText = bugText;
    }

    /**
     *
     * @return status, <RESOLVED, UNRESOLVED, REPLICATED>
     */
    public BugStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status Can be enum <RESOLVED, UNRESOLVED, REPLICATED>
     */
    public void setStatus(BugStatus status) {
        this.status = status;
    }

    /**
     *
     * @return timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp new timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public enum BugStatus {

        UNRESOLVED,
        REPLICATED,
        RESOLVED

    }


}
