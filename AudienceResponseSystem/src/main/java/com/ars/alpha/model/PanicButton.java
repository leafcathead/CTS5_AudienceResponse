package com.ars.alpha.model;

import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@Table(appliesTo = "PanicButton")
public class PanicButton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PanicType")
    private String panicType;

    @Column(name = "Desc")
    private String desc;

    public Long getId() {
        return id;
    }

    public String getPanicType() {
        return panicType;
    }

    public void setPanicType(String panicType) {
        this.panicType = panicType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public void setId(Long id) {
        this.id = id;
    }
}
