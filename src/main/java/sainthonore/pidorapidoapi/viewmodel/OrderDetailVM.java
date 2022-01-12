package sainthonore.pidorapidoapi.viewmodel;

import java.util.List;

import lombok.Data;
import sainthonore.pidorapidoapi.model.Pregunta;

@Data
public class OrderDetailVM {
    List<ProductoVM> productos;
    List<Pregunta> preguntas;
    String ultima_actualizacion;
    Float precio_final;
    Float precio_solo_articulos;
    Float cantidad_articulos_final;
    Float precio_extras_final;
    Float precio_final_sin_impuestos;
    Float impuestos;
    Float precio_adicionales_final;
    Float cantidad_productos_final;

}
