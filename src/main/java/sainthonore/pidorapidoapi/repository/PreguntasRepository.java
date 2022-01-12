package sainthonore.pidorapidoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sainthonore.pidorapidoapi.model.Pregunta;

@Repository
public interface PreguntasRepository extends JpaRepository<Pregunta, Long> {

}
