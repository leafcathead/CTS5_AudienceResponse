package com.ars.alpha.model;


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
@NamedStoredProcedureQuery(name = "favoriteNum", procedureName = "FAVORITE_NUM", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "useless", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "favoriteNum", type = Integer.class)})
public class SessionRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "sessionPassword")
    private String sessionJoinID;

    @OneToOne
    @JoinColumn(name = "OwnerID")
    private SessionUser owner;

    @Column(name = "Timestamp") // This line might have to be removed? Can we do this just in Table definition?
    private Timestamp timestamp;

    public void setOwner(SessionUser owner) {
        this.owner = owner;
    }


    public SessionRoom() {

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
