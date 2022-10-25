package com.ars.alpha.model;

import java.sql.Timestamp;
import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
//@Table(name = "Message")
public class Message {

    private UUID message;
    private Session session;
    private String messageContents;
    private User poster;
    private Timestamp timestamp;

    public Message() {

    }

}
