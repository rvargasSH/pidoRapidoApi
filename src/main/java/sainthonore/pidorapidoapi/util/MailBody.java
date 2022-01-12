package sainthonore.pidorapidoapi.util;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sainthonore.pidorapidoapi.model.OrderInfo;
import sainthonore.pidorapidoapi.model.Pregunta;

@Component
public class MailBody {

    @Value("${email.host}")
    private String mailHost;

    public String customMessage(final String orderCode, String brandName)
            throws UnknownHostException {
        String mailbody = "Buen día<br>";
        mailbody += "Gracias por su compra, su pedido ha sido registrado con el codigo: <b>" + orderCode + "</b>.<br>";
        mailbody += "En breve estaremos haciendo el despacho de sus productos<br>";
        mailbody += "Atentamente.<br>" + brandName;
        return mailbody;
    }

    public String orderDelivery(final String orderCode, String brandName)
            throws UnknownHostException {
        String mailbody = "Buen día<br>";
        mailbody += "Cordialmente le informamos que su pedido  <b>" + orderCode + "</b> ha sido enviado.<br>";
        mailbody += "Atentamente.<br>" + brandName;
        return mailbody;
    }

    public String storeMessage(final String orderCode, String brandName, OrderInfo orderInfo)
            throws UnknownHostException {
        final String link = mailHost + "orders/send-order/" + orderCode;
        String mailbody = "Buen día<br>";
        mailbody += "Se ha registrado un nuevo pedido con código: <b>" + orderCode + "</b>.<br>";
        mailbody += "Los datos del pedido son los siguientes<br>";
        mailbody += "<b>Productos</b><br><ol>";
        mailbody += "<table><tr><th>Producto</th><th>Cantidad</th><th>Precio</th></tr>";
        for (int i = 0; i < orderInfo.getProductos().size(); i++) {
            mailbody += "<tr><td>" + orderInfo.getProductos().get(i).getNombre() + "-"
                    + orderInfo.getProductos().get(i).getDescripcion() + "-"
                    + orderInfo.getProductos().get(i).getVariead() + "<td>";
            mailbody += "<td>" + orderInfo.getProductos().get(i).getCantidad() + "</td>";
            mailbody += "<td>" + orderInfo.getProductos().get(i).getPrecio() + "</td></tr>";
        }
        mailbody += "</table><br>";

        mailbody += "<b>Datos entregados del cliente:</b><br><ol>";

        for (int i = 0; i < orderInfo.getPreguntas().size(); i++) {
            mailbody += "<li><b>" + orderInfo.getPreguntas().get(i).getPregunta() + "</b> "
                    + orderInfo.getPreguntas().get(i).getRespuesta() + "</li>";
        }
        mailbody += "</ol><br>Una vez tenga listo el pedido puede confirmar el envio mediante este link ";
        mailbody += "<a href='" + link + "'>Click aquí</a><br><br>";
        mailbody += "Atentamente.<br>" + brandName;
        return mailbody;
    }
}
