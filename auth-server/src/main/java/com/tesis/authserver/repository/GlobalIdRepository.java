package com.tesis.authserver.repository;

import com.tesis.authserver.entity.GlobalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalIdRepository extends JpaRepository<GlobalId, Integer> {}