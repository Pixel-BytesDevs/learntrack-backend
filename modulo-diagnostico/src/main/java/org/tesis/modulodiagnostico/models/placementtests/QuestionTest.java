package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "questions_tests")
@Check(constraints = "attempts BETWEEN 0 AND 4 AND (grade_penalized IS NULL OR grade_penalized BETWEEN 0.0 AND 1.0)")
public class QuestionTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionBank questionBank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_test_id")
    private TopicTest topicTest;
    private Integer timeTakenInSeconds;

    @Column(name = "attempts", nullable = false)
    private Integer attempts = 0;

    @Column(name = "hints_used", nullable = false)
    private Integer hintsUsed = 0;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "grade_penalized", precision = 4, scale = 2)
    private BigDecimal gradePenalized;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "questionTest",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlternativeQuestionTest> alternativeQuestionTests;

    public QuestionTest(TopicTest topicTest, QuestionBank questionBank) {
        this.topicTest = topicTest;
        this.questionBank = questionBank;
    }

    public QuestionTest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public TopicTest getTopicTest() {
        return topicTest;
    }

    public void setTopicTest(TopicTest topicTest) {
        this.topicTest = topicTest;
    }

    public Integer getTimeTakenInSeconds() {
        return timeTakenInSeconds;
    }

    public void setTimeTakenInSeconds(Integer timeTakenInSeconds) {
        this.timeTakenInSeconds = timeTakenInSeconds;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(Integer hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public BigDecimal getGradePenalized() {
        return gradePenalized;
    }

    public void setGradePenalized(BigDecimal gradePenalized) {
        this.gradePenalized = gradePenalized;
    }

    public List<AlternativeQuestionTest> getAlternativeQuestionTests() {
        return alternativeQuestionTests;
    }

    public void setAlternativeQuestionTests(List<AlternativeQuestionTest> alternativeQuestionTests) {
        this.alternativeQuestionTests = alternativeQuestionTests;
    }
}
