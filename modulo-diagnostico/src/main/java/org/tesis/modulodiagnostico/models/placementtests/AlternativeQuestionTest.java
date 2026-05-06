package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;

@Entity
@Table(name = "alternatives_test")
public class AlternativeQuestionTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_test_id")
    private QuestionTest questionTest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alternative_bank_id")
    private AlternativeBank alternativeBank;
    @Column(name="is_select")
    private Boolean isSelect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionTest getQuestionTest() {
        return questionTest;
    }

    public void setQuestionTest(QuestionTest questionTest) {
        this.questionTest = questionTest;
    }

    public AlternativeBank getAlternativeBank() {
        return alternativeBank;
    }

    public void setAlternativeBank(AlternativeBank alternativeBank) {
        this.alternativeBank = alternativeBank;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }
}
