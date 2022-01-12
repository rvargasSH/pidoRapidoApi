package sainthonore.pidorapidoapi.viewmodel;

import lombok.Data;

@Data
public class ProductoVM {
    String id;
    String nombre;
    String descripcion;
    String variedad;
    String precio;
    String precio_mostrar;
    String precio_anterior;
    String precioanterior_mostrar;
    Float descuento;
    String categoria;
    String subcategoria;
    String ocultar;
    String stock;
    String codigo;
    String imagen;
    String imagen_tamano;
    Boolean tiene_precios_diferentes;
    Boolean se_puede_pedir;
    Float cantidad;
    Float adicionales;
    String aclaraciones;
}
