package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;
import org.tesis.modulodiagnostico.models.Topic;

import java.util.List;

@Entity
@Table(name = "topics_tests")
public class TopicTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfQuestions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_test_id")
    private PlacementTest placementTest;

    @OneToMany(mappedBy = "topicTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionTest> questionTests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public List<QuestionTest> getQuestionTests() {
        return questionTests;
    }

    public void setQuestionTests(List<QuestionTest> questionTests) {
        this.questionTests = questionTests;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public PlacementTest getPlacementTest() {
        return placementTest;
    }

    public void setPlacementTest(PlacementTest placementTest) {
        this.placementTest = placementTest;
    }
}
