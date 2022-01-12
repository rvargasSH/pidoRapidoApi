package sainthonore.pidorapidoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sainthonore.pidorapidoapi.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
