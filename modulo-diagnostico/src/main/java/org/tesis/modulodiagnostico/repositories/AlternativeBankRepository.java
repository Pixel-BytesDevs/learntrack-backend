package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.placementtests.AlternativeBank;
import org.tesis.modulodiagnostico.models.placementtests.QuestionBank;

import java.util.List;

@Repository
public interface AlternativeBankRepository extends JpaRepository<AlternativeBank, Long> {
    List<AlternativeBank> findAllByQuestionBank(QuestionBank questionBank);

}