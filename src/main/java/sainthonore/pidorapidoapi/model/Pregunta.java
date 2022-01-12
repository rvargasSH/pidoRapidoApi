package sainthonore.pidorapidoapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "pregunta")
public class Pregunta extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREGUNTA_SEQ")
    @SequenceGenerator(sequenceName = "PREGUNTA_SEQ", initialValue = 1, allocationSize = 1, name = "PREGUNTA_SEQ")
    private Long preId;

    @Column(nullable = false, unique = false)
    private Long ordId;

    @Column(nullable = false, unique = false)
    private String pregunta;

    @Column(nullable = false, unique = false)
    private String respuesta;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "ordId", insertable = false, updatable = false)
    private OrderInfo orderInfo;
}