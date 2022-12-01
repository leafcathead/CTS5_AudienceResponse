package com.ars.alpha.model;

import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(appliesTo = "QuizResponse")
public abstract class QuizResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Quiz")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "Poster")
    private SessionUser poster;

    @Column(name = "Timestamp")
    private Timestamp timestamp;

    public SessionUser getPoster() {
        return poster;
    }

    public void setPoster(SessionUser poster) {
        this.poster = poster;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
