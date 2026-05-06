package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;
import org.tesis.modulodiagnostico.models.LearningObject;
import org.tesis.modulodiagnostico.models.sesiones.Alumno;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "placement_tests")
public class PlacementTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", insertable = false, updatable = false)
    private Alumno alumno;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private BigDecimal score;
    private Integer durationInSeconds;
    private Integer realDurationInSeconds;

    @Enumerated(EnumType.STRING)
    private PlacementTestStatus status = PlacementTestStatus.IN_PROGRESS;

    @OneToMany(mappedBy = "placementTest", cascade = CascadeType.ALL)
    private List<TopicTest> topicTests = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PlacementTypes placementType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_object_id")
    private LearningObject learningObject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlacementTestStatus getStatus() {
        return status;
    }

    public void setStatus(PlacementTestStatus status) {
        this.status = status;
    }

    public List<TopicTest> getTopicTests() {
        return topicTests;
    }

    public PlacementTypes getPlacementType() {
        return placementType;
    }

    public void setPlacementType(PlacementTypes placementType) {
        this.placementType = placementType;
    }

    public LearningObject getLearningObject() {
        return learningObject;
    }

    public void setLearningObject(LearningObject learningObject) {
        this.learningObject = learningObject;
    }

    public void setTopicTests(List<TopicTest> topicTests) {
        this.topicTests = topicTests;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getRealDurationInSeconds() {
        return realDurationInSeconds;
    }

    public void setRealDurationInSeconds(Integer realDurationInSeconds) {
        this.realDurationInSeconds = realDurationInSeconds;
    }
}
