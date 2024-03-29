package com.ars.alpha.model;

import org.hibernate.annotations.Table;

import javax.persistence.*;

@Table(appliesTo = "Liked")
@Entity
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MessageID")
    private Message likedMessage;

    @ManyToOne
    @JoinColumn(name = "LikerID")
    private SessionUser liker;

    public SessionUser getLiker() {
        return liker;
    }

    public void setLiker(SessionUser liker) {
        this.liker = liker;
    }

    public Message getLikedMessage() {
        return likedMessage;
    }

    public void setLikedMessage(Message likedMessage) {
        this.likedMessage = likedMessage;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * toString method
     * @return String representation of Liked
     */
    @Override
    public String toString() {
        return "Liked{" +
                "id=" + id +
                ", likedMessage=" + likedMessage +
                ", liker=" + liker +
                '}';
    }
}
