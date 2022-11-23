package com.ars.alpha.model;

import com.ars.alpha.dao.MessageRepository;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
//@Table(name = "Message")

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
//@NamedStoredProcedureQuery(name = "RETRIEVE_MESSAGES", procedureName = "RETRIEVE_MESSAGES", parameters = {
//        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class)})
@NamedStoredProcedureQuery(name = "RETRIEVE_MESSAGES", procedureName = "RETRIEVE_MESSAGES", resultClasses = {Message.class}, parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class)})
@NamedStoredProcedureQuery(name = "UPDATE_MESSAGE", procedureName = "UPDATE_MESSAGE", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "messageID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "posterID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "newBody", type = String.class)})
//@SqlResultSetMapping(name = "Mapping.Message", // I THINK THIS IS UNNEEDED. I HOPE TO GOD IT IS NOT NEEDED
//                     classes = @ConstructorResult(targetClass = Message.class,
//                               columns = {@ColumnResult(name = "ID"),
//                                            @ColumnResult(name = "SessionID"),
//                                            @ColumnResult(name = "MsgContent"),
//                                             @ColumnResult(name = "PosterID"),
//                                             @ColumnResult(name = "ReplyTo"),
//                                             @ColumnResult(name = "Likes"),
//                                             @ColumnResult(name = "IS_APPROVED"),
//                                             @ColumnResult(name = "Timestamp")}))
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
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
    //  private Session session;
  //  private User poster;

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

    public Message() {

    }

    public boolean checkOverSize(){
        if (messageContent.length() >= 1024) return true;
        return false;
    }

    public boolean checkBlasphemy() {
        //Body of the check
        return false;
    }

    public Message(Long ID, SessionUser user, String messageContent, int likes, boolean visible, Message replyTo, Timestamp timestamp) {
        this.id = ID;
        this.poster = user;
        this.messageContent = messageContent;
        this.likes = likes;
        this.visible = visible;
        this.replyTo = replyTo;
        this.timestamp = timestamp;
    }

    public Message(Long id) {
        this.id = id;
    }

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
