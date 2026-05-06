package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tesis.modulodiagnostico.models.usuarios.TopicUser;

import java.util.List;
import java.util.Optional;

public interface TopicUserRepository extends JpaRepository<TopicUser, Long> {

    @Query(value = """
            SELECT * FROM topics_users WHERE topic_id=:topicId AND id_usuario=:usuarioId
            """, nativeQuery = true)
    List<TopicUser> findByUserAndTopic(@Param("usuarioId") Long usuarioId, @Param("topicId") String topicId);

    @Query(value = """
            SELECT * FROM topics_users WHERE topic_id=:topicId AND id_usuario=:userId
            """, nativeQuery = true)
    TopicUser findByTopicIdAndUserId(@Param("topicId") String topicId, @Param("userId") Long userId);


    List<TopicUser> findAllByIdUsuario(Long usuarioId);

}
