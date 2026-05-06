package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.Difficulty;
import org.tesis.modulodiagnostico.models.placementtests.QuestionBank;

import java.util.List;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {
    List<QuestionBank> findAllByTopic(Topic topic);

    @Query(value = """
            SELECT * 
            FROM questions_bank q
            WHERE q.topic_id = :topicId
              AND q.difficulty_id = :difficultyId
            ORDER BY RANDOM()
            LIMIT :limit
            """, nativeQuery = true)
    List<QuestionBank> findRandomByTopicAndDifficulty(
            @Param("topicId") String topicId,
            @Param("difficultyId") Long difficultyId,
            @Param("limit") int limit);

    @Query(value = "SELECT * FROM questions_bank q WHERE q.topic_id = :topicId AND q.difficulty_id = :difficultyId ORDER BY random() LIMIT :limit", nativeQuery = true)
    List<QuestionBank> findRandomByTopicIdAndDifficultyId(@Param("topicId") String topicId, @Param("difficultyId") Long difficultyId, @Param("limit") int limit);

    List<QuestionBank> findByTopic_Id(String topicId);

    @Query(value = """
                SELECT * FROM questions_bank 
                    WHERE difficulty_id = :difficultyId AND topic_id = :topicId 
                    ORDER BY RANDOM()
                    LIMIT 2
            """, nativeQuery = true)
    List<QuestionBank> findTwoRandomQuestionsByDifficultyAndTopic(@Param("difficultyId") Long difficultyId, @Param("topicId") String topicId);

    @Query(value = """
                SELECT * FROM questions_bank 
                    WHERE difficulty_id = :difficultyId AND topic_id = :topicId 
                    ORDER BY RANDOM()
                    LIMIT 4
            """, nativeQuery = true)
    List<QuestionBank> findFourRandomQuestionsByDifficultyAndTopic(@Param("difficultyId") Long difficultyId, @Param("topicId") String topicId);
}