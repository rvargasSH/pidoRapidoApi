package sainthonore.pidorapidoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sainthonore.pidorapidoapi.model.Pregunta;

@Repository
public interface PreguntasRepository extends JpaRepository<Pregunta, Long> {
    Optional<Pregunta> findByOrdIdAndPregunta(Long ordId, String pregunta);
}
