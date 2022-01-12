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
@Table(name = "store")
public class Store extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_SEQ")
    @SequenceGenerator(sequenceName = "STORE_SEQ", initialValue = 1, allocationSize = 1, name = "STORE_SEQ")
    private Long stoId;

    @Column(nullable = false, unique = false)
    private Long braId;

    @Column(nullable = false, unique = true)
    private String stoCode;

    @Column(nullable = true, unique = true)
    private String name;

    @Column(nullable = true, unique = true)
    private String address;

    @Column(nullable = true, unique = false)
    private String notificationMail;

    @Column(nullable = true, unique = false)
    private String urlWebPage;
}