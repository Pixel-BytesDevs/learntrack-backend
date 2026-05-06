package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.placementtests.AlternativeQuestionTest;

@Repository
public interface AlternativeTestRepository  extends JpaRepository<AlternativeQuestionTest, Long> {
}
