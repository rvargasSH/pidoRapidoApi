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
@Table(name = "brand")
public class Brand extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRAND_SEQ")
    @SequenceGenerator(sequenceName = "BRAND_SEQ", initialValue = 1, allocationSize = 1, name = "BRAND_SEQ")
    private Long braId;

    @Column(nullable = true, unique = true)
    private String name;

    @Column(nullable = true, unique = false)
    private String keyMercadoPago;
}