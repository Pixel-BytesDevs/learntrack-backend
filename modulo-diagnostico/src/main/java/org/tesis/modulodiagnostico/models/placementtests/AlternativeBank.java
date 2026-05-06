package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;

@Entity
@Table(name = "alternatives_bank")
public class AlternativeBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean isCorrect;
    private String latexRepresentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionBank questionBank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatexRepresentation() {
        return latexRepresentation;
    }

    public void setLatexRepresentation(String latexRepresentation) {
        this.latexRepresentation = latexRepresentation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }
}
