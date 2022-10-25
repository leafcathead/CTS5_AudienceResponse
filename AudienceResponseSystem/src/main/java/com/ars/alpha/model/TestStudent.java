package com.ars.alpha.model;

import javax.persistence.*;

@Entity
@Table(name="Student")
public class TestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "DisplayName")
    private String displayName;

    public TestStudent (String displayName) {
        this.displayName = displayName;
    }

    public TestStudent() {

    }

    public Long getId() {
        return ID;
    }

    public void setId(Long id) {
        this.ID = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
