package org.tesis.modulodiagnostico.services.impl.placement;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.PlacementTest;
import org.tesis.modulodiagnostico.models.placementtests.TopicTest;
import org.tesis.modulodiagnostico.repositories.TopicTestRepository;

import java.util.List;

@Service
public class TopicTestServiceImpl {

    private final Integer NUMBER_OF_QUESTIONS_PER_TOPIC = 2;

    private final TopicTestRepository topicTestRepository;

    public TopicTestServiceImpl(TopicTestRepository topicTestRepository) {
        this.topicTestRepository = topicTestRepository;
    }

    public TopicTest findById(Topic topic) {
        return topicTestRepository.findByTopic(topic);
    }

    public List<TopicTest> findAllByPlacementTest(PlacementTest placementTest) {
        return topicTestRepository.findAllByPlacementTest(placementTest);
    }

    public TopicTest save(TopicTest topicTest) {
        return topicTestRepository.save(topicTest);
    }

    public List<TopicTest> saveAll(List<TopicTest> topicTests) {
        return topicTestRepository.saveAll(topicTests);
    }

    public List<TopicTest> createTopicTestsForPlacementTest(PlacementTest placementTest, List<Topic> topics) {
        List<TopicTest> topicTests = topics.stream().map(
                topic -> {
                    TopicTest topicTest = new TopicTest();
                    topicTest.setPlacementTest(placementTest);
                    topicTest.setTopic(topic);
                    topicTest.setNumberOfQuestions(NUMBER_OF_QUESTIONS_PER_TOPIC);
                    return topicTest;
                }
        ).toList();
        return saveAll(topicTests);
    }
}
