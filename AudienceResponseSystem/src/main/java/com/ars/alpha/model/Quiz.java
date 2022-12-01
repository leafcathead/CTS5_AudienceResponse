package com.ars.alpha.model;

import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@Table(appliesTo = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "QuizType")
    private QuizType quizType;
    @ManyToOne
    @JoinColumn(name = "SessionRoom")
    private SessionRoom session;

    public SessionRoom getSession() {
        return session;
    }

    public void setSession(SessionRoom session) {
        this.session = session;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
