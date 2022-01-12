package sainthonore.pidorapidoapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

@Data
@Entity
@Table(name = "order_info")
public class OrderInfo extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_INFO_SEQ")
    @SequenceGenerator(sequenceName = "ORDER_INFO_SEQ", initialValue = 1, allocationSize = 1, name = "ORDER_INFO_SEQ")
    private Long ordId;

    @Column(nullable = false, unique = false)
    private Long braId;

    @Column(nullable = false, unique = false)
    private Long storeId;

    @Column(nullable = true, unique = false)
    private String ultimaActualizacion;

    @Column(nullable = true, unique = false)
    private Float precioFinal;

    @Column(nullable = true, unique = false)
    private Float precioSoloArticulos;

    @Column(nullable = true, unique = false)
    private Float cantidadArticulosFinal;

    @Column(nullable = true, unique = false)
    private Float precioExtrasFinal;

    @Column(nullable = true, unique = false)
    private Float precioFinalSinImpuestos;

    @Column(nullable = true, unique = false)
    private Float impuestos;

    @Column(nullable = true, unique = false)
    private Float precioAdicionalesFinal;

    @Column(nullable = true, unique = false)
    private Float cantidadproductosFinal;

    @Column(nullable = true, unique = false)
    private Long orsId;

    @Column(nullable = true, unique = true)
    private String oriCode;

    @Column(nullable = true, unique = true)
    private String sandboxinitpoint;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "orderInfo")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Producto> productos = new ArrayList<Producto>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "orderInfo")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Pregunta> preguntas = new ArrayList<Pregunta>();

    @OneToOne()
    @JoinColumn(name = "braId", referencedColumnName = "braId", insertable = false, updatable = false)
    private Brand brand;

    @OneToOne()
    @JoinColumn(name = "storeId", referencedColumnName = "stoId", insertable = false, updatable = false)
    private Store store;

}
