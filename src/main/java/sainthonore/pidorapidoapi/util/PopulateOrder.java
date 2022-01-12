package sainthonore.pidorapidoapi.util;

import java.util.Date;

import org.hibernate.sql.ordering.antlr.OrderingSpecification.Ordering;
import org.springframework.stereotype.Component;

import sainthonore.pidorapidoapi.model.OrderInfo;
import sainthonore.pidorapidoapi.model.Producto;
import sainthonore.pidorapidoapi.viewmodel.OrderInfoVM;
import sainthonore.pidorapidoapi.viewmodel.ProductoVM;

@Component
public class PopulateOrder {

    public OrderInfo populateOrder(OrderInfoVM infoOrder, Long bradId, Long storeId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setBraId(bradId);
        orderInfo.setStoreId(storeId);
        orderInfo.setCantidadArticulosFinal(infoOrder.getOrder().getCantidad_articulos_final());
        orderInfo.setCantidadproductosFinal(infoOrder.getOrder().getCantidad_productos_final());
        orderInfo.setCreatedAt(new Date());
        orderInfo.setImpuestos(infoOrder.getOrder().getImpuestos());
        orderInfo.setOrsId(Long.parseLong("1"));
        orderInfo.setPrecioAdicionalesFinal(infoOrder.getOrder().getPrecio_adicionales_final());
        orderInfo.setPrecioExtrasFinal(infoOrder.getOrder().getPrecio_extras_final());
        orderInfo.setPrecioFinal(infoOrder.getOrder().getPrecio_final());
        orderInfo.setPrecioFinalSinImpuestos(infoOrder.getOrder().getPrecio_final_sin_impuestos());
        orderInfo.setPrecioSoloArticulos(infoOrder.getOrder().getPrecio_solo_articulos());
        orderInfo.setUltimaActualizacion(infoOrder.getOrder().getUltima_actualizacion());
        return orderInfo;
    }

    public Producto populateProducto(ProductoVM productoInfo, Long orderId) {
        Producto productoReturn = new Producto();
        productoReturn.setAclaracion(productoInfo.getAclaraciones());
        productoReturn.setAdicionales(productoInfo.getAdicionales());
        productoReturn.setCantidad(productoInfo.getCantidad());
        productoReturn.setCategoria(productoInfo.getCategoria());
        productoReturn.setCodigo(productoInfo.getCodigo());
        productoReturn.setCreatedAt(new Date());
        productoReturn.setDescripcion(productoInfo.getDescripcion());
        productoReturn.setDescuento(productoInfo.getDescuento());
        productoReturn.setImagen(productoInfo.getImagen());
        productoReturn.setImagenTamano(productoInfo.getImagen_tamano());
        productoReturn.setNombre(productoInfo.getNombre());
        productoReturn.setOcultar(productoInfo.getOcultar());
        productoReturn.setOrdId(orderId);
        productoReturn.setPrecio(productoInfo.getPrecio());
        productoReturn.setSePuedePedir(productoInfo.getSe_puede_pedir());
        productoReturn.setStock(productoInfo.getStock());
        productoReturn.setSubcategoria(productoInfo.getSubcategoria());
        productoReturn.setTienePreciosDiferentes(productoInfo.getTiene_precios_diferentes());
        productoReturn.setVariead(productoInfo.getVariedad());

        return productoReturn;
    }
}
