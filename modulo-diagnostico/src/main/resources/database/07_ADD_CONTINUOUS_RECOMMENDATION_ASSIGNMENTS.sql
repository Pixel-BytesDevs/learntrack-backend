CREATE TABLE IF NOT EXISTS continuous_recommendation_assignments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    topic_id VARCHAR(64) NOT NULL,
    placement_test_id BIGINT NOT NULL,
    recommended_difficulty VARCHAR(16) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    consumed_at TIMESTAMP NULL
);

CREATE INDEX IF NOT EXISTS idx_continuous_assignment_lookup
    ON continuous_recommendation_assignments (placement_test_id, user_id, created_at DESC);
