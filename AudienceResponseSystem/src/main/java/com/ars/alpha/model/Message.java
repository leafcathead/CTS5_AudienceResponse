package com.ars.alpha.model;

import com.ars.alpha.dao.MessageRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "Message")

@NamedStoredProcedureQuery(name = "INSERT_MESSAGE", procedureName = "INSERT_MESSAGE", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PosterID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "SessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "MsgContent", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "MessageID", type = Long.class)
})
@NamedStoredProcedureQuery(name = "INSERT_REPLY", procedureName = "INSERT_REPLY", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "posterID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "replyToID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "msgContent", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "newMessageID", type = Long.class) })
@NamedStoredProcedureQuery(name = "RETRIEVE_MESSAGES", procedureName = "RETRIEVE_MESSAGES", resultClasses = {Message.class}, parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class)})
@NamedStoredProcedureQuery(name = "UPDATE_MESSAGE", procedureName = "UPDATE_MESSAGE", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "messageID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "posterID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "newBody", type = String.class)})
@NamedStoredProcedureQuery(name = "FLIP_VISIBILITY", procedureName = "FLIP_VISIBILITY", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "messageID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "posterID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class)})
@NamedStoredProcedureQuery(name = "DELETE_MESSAGE", procedureName = "DELETE_MESSAGE", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "posterID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "messageID", type = Long.class)
})
@NamedStoredProcedureQuery(name = "LIKE_MESSAGE", procedureName = "LIKE_MESSAGE", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "messageID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "likerID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "liked", type = Boolean.class)
})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Message {
    @Id
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="id", scope = Message.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "MsgContent", nullable = false)
    private String messageContent;

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

    @Column(name="IsApproved", nullable = false)
    private boolean visible = false;

    @Column(name="Likes")
    private int likes = 0;

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


    public String getMessageContents() {
        return this.messageContent;
    }

    public void setMessageContent(String newMessage) {
        this.messageContent = newMessage;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLikes() {
        return this.likes;
    }

    public boolean getVisible() {
        return this.visible;
    }


    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    /**
     * Default constructor for Message
     */
    public Message() {

    }

    /**
     * Checks if message content is too long to be inserted into DB without truncation.
     * @return returns if the object is over size
     */
    public boolean checkOverSize(){
        if (messageContent.length() >= 1024) return true;
        return false;
    }


    /**
     * Constructor for Message
     * @param ID messageID
     * @param user userID
     * @param messageContent body text of message
     * @param likes number of likes, defaults to 0
     * @param visible whether the message is visible, defaults to false
     * @param replyTo ID of message
     * @param timestamp time inserted into DB
     */
    public Message(Long ID, SessionUser user, String messageContent, int likes, boolean visible, Message replyTo, Timestamp timestamp) {
        this.id = ID;
        this.poster = user;
        this.messageContent = messageContent;
        this.likes = likes;
        this.visible = visible;
        this.replyTo = replyTo;
        this.timestamp = timestamp;
    }

    /**
     * Constructor with only ID
     * @param id MessageID
     */
    public Message(Long id) {
        this.id = id;
    }

    /**
     * toString()
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageContent='" + messageContent + '\'' +
                ", poster=" + poster +
                ", session=" + session +
                ", replyTo=" + replyTo +
                '}';
    }
}
