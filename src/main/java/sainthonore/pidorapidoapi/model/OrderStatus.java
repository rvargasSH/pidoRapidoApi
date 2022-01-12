package sainthonore.pidorapidoapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_status")
public class OrderStatus extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERSTATUS_SEQ")
    @SequenceGenerator(sequenceName = "ORDERSTATUS_SEQ", initialValue = 1, allocationSize = 1, name = "ORDERSTATUS_SEQ")
    private Long orsId;

    @Column(nullable = true, unique = true)
    private String description;
}