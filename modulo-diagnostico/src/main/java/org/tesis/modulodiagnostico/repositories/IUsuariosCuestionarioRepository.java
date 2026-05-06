package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.usuarios.UsuariosCuestionario;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuariosCuestionarioRepository extends JpaRepository<UsuariosCuestionario,Long> {

    @Modifying
    @Query("""
        delete from UsuariosCuestionario uc
        where uc.usuarioId = :usuarioId 
        and uc.pregunta.id in :preguntaIds
    """)
    void deleteByUsuarioAndPreguntas(@Param("usuarioId") Long usuarioId,
                                     @Param("preguntaIds") Collection<Long> preguntaIds);

    @Query("""
       select a.estiloVark.id as estiloVarkId, count(uc.id) as total
       from UsuariosCuestionario uc
       join uc.alternativa a
       where uc.usuarioId = :usuarioId
       group by a.estiloVark.id
    """)
    List<Object[]> countSeleccionesPorEstilo(@Param("usuarioId") Long usuarioId);
}
