package sainthonore.pidorapidoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sainthonore.pidorapidoapi.model.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

    Optional<OrderInfo> findByOriCode(String orICode);

}
