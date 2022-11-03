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

    @ManyToOne
    @JoinColumn(name = "PosterID")
    private SessionUser poster;

    @ManyToOne
    @JoinColumn(name = "SessionID")
    private SessionRoom session;
    @ManyToOne
    @JoinColumn(name = "ReplyTo")
    private Message replyTo;


    @Column(name="Timestamp", nullable = false, updatable = false)
    private Timestamp timestamp;

    public Message getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Message replyTo) {
        this.replyTo = replyTo;
    }

    public SessionRoom getSession() {
        return session;
    }

    public void setSession(SessionRoom session) {
        this.session = session;
    }

    public SessionUser getPoster() {
        return poster;
    }

    public void setPoster(SessionUser poster) {
        this.poster = poster;
    }
    //  private Session session;
  //  private User poster;

    public String getMessageContents() {
        return this.messageContents;
    }

    public void setMessageContents(String newMessage) {
        this.messageContents = newMessage;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message() {

    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageContents='" + messageContents + '\'' +
                ", poster=" + poster +
                ", session=" + session +
                ", replyTo=" + replyTo +
                '}';
    }
}
