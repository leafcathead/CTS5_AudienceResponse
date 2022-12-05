package com.ars.alpha.model;

import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@Table(appliesTo = "QuizType")
public class QuizType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Abbrev")
    private String Abbrev;

    @Column(name = "Desc")
    private String Desc;

    public String getAbbrev() {
        return Abbrev;
    }

    public void setAbbrev(String abbrev) {
        Abbrev = abbrev;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum TypeAbbrev {

    }

}
