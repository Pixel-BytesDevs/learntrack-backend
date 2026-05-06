package org.tesis.modulodiagnostico.models.recommendations;

import jakarta.persistence.*;
import org.tesis.modulodiagnostico.models.placementtests.Difficulties;

import java.time.LocalDateTime;

@Entity
@Table(name = "continuous_recommendation_assignments")
public class ContinuousRecommendationAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "topic_id", nullable = false, length = 64)
    private String topicId;

    @Column(name = "placement_test_id", nullable = false)
    private Long placementTestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommended_difficulty", nullable = false)
    private Difficulties recommendedDifficulty;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "consumed_at")
    private LocalDateTime consumedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Long getPlacementTestId() {
        return placementTestId;
    }

    public void setPlacementTestId(Long placementTestId) {
        this.placementTestId = placementTestId;
    }

    public Difficulties getRecommendedDifficulty() {
        return recommendedDifficulty;
    }

    public void setRecommendedDifficulty(Difficulties recommendedDifficulty) {
        this.recommendedDifficulty = recommendedDifficulty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getConsumedAt() {
        return consumedAt;
    }

    public void setConsumedAt(LocalDateTime consumedAt) {
        this.consumedAt = consumedAt;
    }
}
