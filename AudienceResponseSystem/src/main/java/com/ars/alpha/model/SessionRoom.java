package com.ars.alpha.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.type.descriptor.sql.NVarcharTypeDescriptor;

import javax.lang.model.type.NullType;
import javax.persistence.*;
import java.sql.Timestamp;

// These will work once we know what database we use. For now they will cause errors.
// @Entity
@Entity
@Table(name = "SessionRoom")
@NamedStoredProcedureQuery(name = "CREATE_SESSION", procedureName = "CREATE_SESSION", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "newSessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "newUserID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "randomPassword", type = String.class) })
@NamedStoredProcedureQuery(name = "GET_SESSION_ROOM_ID_FROM_PASSWORD", procedureName = "GET_SESSION_ROOM_ID_FROM_PASSWORD", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionPassword", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "sessionID", type = Long.class) })
@NamedStoredProcedureQuery(name = "JOIN_SESSION", procedureName = "JOIN_SESSION", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.INOUT, name = "newUserID", type = Long.class) })
@NamedStoredProcedureQuery(name = "CLOSE_SESSION", procedureName = "CLOSE_SESSION", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "sessionID", type = Long.class) })
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SessionRoom {
    @Id
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="id", scope = SessionRoom.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "sessionPassword")
    private String sessionJoinID;

    @OneToOne
    @JoinColumn(name = "OwnerID")
    private SessionUser owner;

    @Column(name = "Timestamp")
    private Timestamp timestamp;

    public void setOwner(SessionUser owner) {
        this.owner = owner;
    }


    public SessionRoom() {

    }

    /**
     * Constructor used in comment POST. Constructed from the SessionUser class
     * @param id
     */
    public SessionRoom(Long id) {
        this.id = id;
    }


    public Long getID() {
        return id;
    }

    public SessionUser getOwner() {
        return owner;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setID(Long ID) {
        this.id = ID;
    }



}
