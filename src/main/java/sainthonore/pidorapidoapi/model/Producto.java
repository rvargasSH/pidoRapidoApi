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
@Table(name = "producto")
public class Producto extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTO_SEQ")
    @SequenceGenerator(sequenceName = "PRODUCTO_SEQ", initialValue = 1, allocationSize = 1, name = "PRODUCTO_SEQ")
    private Long proId;

    @Column(nullable = false, unique = false)
    private Long ordId;

    @Column(nullable = true, unique = false)
    private String nombre;

    @Column(nullable = true, unique = false)
    private String descripcion;

    @Column(nullable = true, unique = false)
    private String variead;

    @Column(nullable = true, unique = false)
    private String precio;

    @Column(nullable = true, unique = false)
    private String precioMostrar;

    @Column(nullable = true, unique = false)
    private Float descuento;

    @Column(nullable = true, unique = false)
    private String categoria;

    @Column(nullable = true, unique = false)
    private String subcategoria;

    @Column(nullable = true, unique = false)
    private String ocultar;

    @Column(nullable = true, unique = false)
    private String stock;

    @Column(nullable = true, unique = false)
    private String codigo;

    @Column(nullable = true, unique = false)
    private String imagen;

    @Column(nullable = true, unique = false)
    private String imagenTamano;

    @Column(nullable = true, unique = false)
    private Boolean tienePreciosDiferentes;

    @Column(nullable = true, unique = false)
    private Boolean sePuedePedir;

    @Column(nullable = true, unique = false)
    private Float cantidad;

    @Column(nullable = true, unique = false)
    private Float adicionales;

    @Column(nullable = true, unique = false)
    private String aclaracion;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "ordId", insertable = false, updatable = false)
    private OrderInfo orderInfo;

}