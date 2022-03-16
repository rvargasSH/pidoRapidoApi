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

        public String customMessage(final String orderCode, String customName, OrderInfo orderInfo)
                        throws UnknownHostException {
                String mailbody = "¡Hola " + customName + " !<br><br>";
                Float totalPrice = (float) 0;
                mailbody += "Muchas gracias por comprar en nuestro catálogo digital. Comenzaremos a preparar tu pedido y te mantendremos al tanto de cualquier novedad.<br><br>";
                mailbody += "Tu orden ha sido registrada bajo el <b>código " + orderCode + "</b>.<br>";
                mailbody += "Los detalles de tu compra son:<br>";
                mailbody += "<table><tr><th style='border: 1px solid black'>Producto</th><th style='border: 1px solid black'>Cantidad</th><th style='border: 1px solid black'>Precio</th></tr>";
                for (int i = 0; i < orderInfo.getProductos().size(); i++) {
                        mailbody += "<tr><td style='border: 1px solid black'>"
                                        + orderInfo.getProductos().get(i).getNombre()
                                        + "</td>";
                        mailbody += "<td style='border: 1px solid black'>"
                                        + orderInfo.getProductos().get(i).getCantidad()
                                        + "</td>";
                        mailbody += "<td style='border: 1px solid black'>$"
                                        + orderInfo.getProductos().get(i).getPrecio()
                                        + "</td></tr>";
                        totalPrice += Float.parseFloat(orderInfo.getProductos().get(i).getPrecio());
                }
                mailbody += "<tr><td><b>Subtotal</b></td><td colspan='2'><b>" + totalPrice + "</b></td></tr>";
                mailbody += "</table><br>";
                mailbody += "<b>El tiempo estimado de envío es de " + orderInfo.getStore().getTiempoEntrega()
                                + " días hábiles</b>, agradecemos tu comprensión.<br><br>";
                mailbody += "Si tienes cualquier duda o problema con respecto a tu orden, <b>no dudes en escribirnos a nuestro Whatsapp +56 9 3955 4162.</b><br><br>";
                mailbody += "¡Nos vemos pronto!.<br>";
                return mailbody;
        }

        public String orderDelivery(final String orderCode, String customName, String deliveryTime)
                        throws UnknownHostException {
                String mailbody = "¡Hola " + customName + " !<br><br>";
                mailbody += "Tenemos buenas noticias: tu pedido ya fue preparado y está listo para ser enviado.<br><br>";
                mailbody += "Recuerda que los <b>tiempos de despacho son entre " + deliveryTime
                                + " días hábiles</b>.<br><br>";
                mailbody += "Al recibir tu pedido, deberás mostrar este e-mail a nuestro transportista. Si es otra persona la que va a recibir el paquete, asegúrate de enviarle este e-mail o una foto de él.<br><br>";
                mailbody += "Si tienes cualquier duda o problema con respecto a tu orden, no dudes en escribirnos a nuestro <b>Whatsapp +56 9 3955 4162</b>.<br><br>";
                mailbody += "¡Nos vemos pronto!";
                return mailbody;
        }

        public String storeMessage(final String orderCode, String brandName, OrderInfo orderInfo)
                        throws UnknownHostException {
                final String link = mailHost + "orders/send-order/" + orderCode;
                String mailbody = "¡Hola Equipo " + brandName + "!<br><br>";
                Float totalPrice = (float) 0;
                mailbody += "Hemos recibido un nuevo pedido a través de nuestro catálogo digital. <br><br>";
                mailbody += "La orden ha sido registrada bajo el <b>código " + orderCode
                                + "</b>. A continuación verás el detalle de la compra:<br>";
                mailbody += "<table><tr><th style='border: 1px solid black'>Producto</th><th style='border: 1px solid black'>Cantidad</th><th style='border: 1px solid black'>Precio</th></tr>";
                for (int i = 0; i < orderInfo.getProductos().size(); i++) {
                        mailbody += "<tr><td style='border: 1px solid black'>"
                                        + orderInfo.getProductos().get(i).getNombre()
                                        + "</td>";
                        mailbody += "<td style='border: 1px solid black'>"
                                        + orderInfo.getProductos().get(i).getCantidad()
                                        + "</td>";
                        mailbody += "<td style='border: 1px solid black'>$"
                                        + orderInfo.getProductos().get(i).getPrecio()
                                        + "</td></tr>";
                        totalPrice += Float.parseFloat(orderInfo.getProductos().get(i).getPrecio());
                }
                mailbody += "<tr><td><b>Subtotal</b></td><td colspan='2'><b>" + totalPrice + "</b></td></tr>";
                mailbody += "</table><br>";
                mailbody += "<b>Los datos del cliente son:</b><br><ol>";

                for (int i = 0; i < orderInfo.getPreguntas().size(); i++) {
                        mailbody += "<li><b>" + orderInfo.getPreguntas().get(i).getPregunta() + "</b> "
                                        + orderInfo.getPreguntas().get(i).getRespuesta() + "</li>";
                }
                mailbody += "</ol><br><br>";
                mailbody += "Para hacer esta venta sigue los siguientes pasos:<br>";
                mailbody += "1.- Prepara el pedido y una vez lo tengas listo haz <a href='" + link
                                + "'>Click aquí</a><br>";
                mailbody += "2.- Procesa la venta como harías normalmente y pon 'efectivo' como medio de pago. No olvides marcar la casilla 'catálogo' e ingresar los datos del cliente.<br>";
                mailbody += "3.- Da aviso a tu supervisora para coordinar el envío.<br>";
                mailbody += "4.- Listo<br><br>";
                mailbody += "Cualquier pregunta que tengas, no dudes en decirle a tu supervisora.<br>";
                mailbody += "¡Muchas gracias por tu ayuda!<br><br>";
                return mailbody;
        }
}
