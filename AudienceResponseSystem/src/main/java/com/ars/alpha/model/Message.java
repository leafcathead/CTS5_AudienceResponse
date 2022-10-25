package com.ars.alpha.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
//@Table(name = "Message")

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MsgContents", nullable = false)
    private String messageContents;


  //  private Session session;
  //  private User poster;
    private Timestamp timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message() {

    }

}
