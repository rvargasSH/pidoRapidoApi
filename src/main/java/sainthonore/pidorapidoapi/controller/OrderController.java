package sainthonore.pidorapidoapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sainthonore.pidorapidoapi.model.OrderInfo;
import sainthonore.pidorapidoapi.model.Pregunta;
import sainthonore.pidorapidoapi.model.Store;
import sainthonore.pidorapidoapi.repository.OrderInfoRepository;
import sainthonore.pidorapidoapi.repository.PreguntasRepository;
import sainthonore.pidorapidoapi.repository.ProductoRepository;
import sainthonore.pidorapidoapi.repository.StoreRepository;
import sainthonore.pidorapidoapi.service.MercadoPagoService;
import sainthonore.pidorapidoapi.util.MailBody;
import sainthonore.pidorapidoapi.util.PopulateOrder;
import sainthonore.pidorapidoapi.util.ResponseUtil;
import sainthonore.pidorapidoapi.util.SendMail;
import sainthonore.pidorapidoapi.viewmodel.MailVM;
import sainthonore.pidorapidoapi.viewmodel.MercadoPagoResponse;
import sainthonore.pidorapidoapi.viewmodel.OrderInfoVM;
import sainthonore.pidorapidoapi.viewmodel.RedirectResponseVM;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PreguntasRepository preguntasRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PopulateOrder populateOrder;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    SendMail sendMail;

    @Autowired
    MailBody mailBody;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> index() {
        return null;
    }

    @RequestMapping(value = "order-info/{stoCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody OrderInfoVM model, @PathVariable String stoCode) throws Exception {
        System.out.println("come to create order");
        Optional<Store> storeInfo = storeRepository.findByStoCode(stoCode);
        if (storeInfo.isPresent()) {
            OrderInfo newOrdersaved = orderInfoRepository.saveAndFlush(
                    populateOrder.populateOrder(model, storeInfo.get().getBraId(),
                            storeInfo.get().getStoId()));

            for (int i = 0; i < model.getOrder().getProductos().size(); i++) {
                productoRepository.save(populateOrder.populateProducto(model.getOrder().getProductos().get(i),
                        newOrdersaved.getOrdId()));
            }
            for (int i = 0; i < model.getOrder().getPreguntas().size(); i++) {
                model.getOrder().getPreguntas().get(i).setOrdId(newOrdersaved.getOrdId());
                preguntasRepository.save(model.getOrder().getPreguntas().get(i));
            }
            String orderCode = "O" + storeInfo.get().getName().charAt(0) + newOrdersaved.getOrdId();
            newOrdersaved.setOriCode(orderCode);
            orderInfoRepository.save(newOrdersaved);
            // Checkout Mercado Pago
            RedirectResponseVM responseVM = new RedirectResponseVM();
            responseVM.setRedirect_url(
                    mercadoPagoService.createPreference(model.getOrder().getProductos(), orderCode));
            return ResponseEntity.ok(responseVM);
        } else {
            return ResponseEntity.badRequest()
                    .body("No existe la tienda desde la cual esta intentando enviar la orden");
        }
    }

    @RequestMapping(value = "save-mail/{orderCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNail(@RequestBody MailVM model, @PathVariable String orderCode) throws Exception {

        Optional<OrderInfo> orderInfo = orderInfoRepository.findByOriCode(orderCode);
        if (orderInfo.isPresent()) {
            Pregunta pregunta = new Pregunta();
            pregunta.setCreatedAt(new Date());
            pregunta.setOrdId(orderInfo.get().getOrdId());
            pregunta.setPregunta("Email");
            pregunta.setRespuesta(model.getEmail());
            preguntasRepository.save(pregunta);
            return ResponseEntity.ok("Saved");
        } else {
            return ResponseEntity.badRequest()
                    .body("No existe la tienda desde la cual esta intentando enviar la orden");
        }
    }

    // Index(Get All)
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ResponseEntity<?> success(@RequestParam String collection_id,
            @RequestParam String collection_status,
            @RequestParam String payment_id, @RequestParam String status, @RequestParam String external_reference,
            @RequestParam String merchant_order_id, @RequestParam String preference_id)
            throws UnknownHostException, MessagingException, URISyntaxException {

        Optional<OrderInfo> orderInfo = orderInfoRepository.findByOriCode(external_reference);
        if (orderInfo.isPresent()) {
            // Set status 2 (payed)
            orderInfo.get().setOrsId(Long.parseLong("2"));
            orderInfo.get().setLastModifiedAt(new Date());

            // Send mail to the custom
            sendMail.singleAddress(getEmail(orderInfo.get().getPreguntas()), "Pedido registrado",
                    mailBody.customMessage(orderInfo.get().getOriCode(), orderInfo.get().getBrand().getName()));
            // Send mail to store
            sendMail.singleAddress(orderInfo.get().getStore().getNotificationMail(), "Pedido registrado",
                    mailBody.storeMessage(orderInfo.get().getOriCode(), orderInfo.get().getBrand().getName(),
                            orderInfo.get()));
            Optional<Store> storeInfo = storeRepository.findById(orderInfo.get().getStoreId());
            return ResponseEntity.ok(storeInfo.get().getUrlWebPage());
        } else {
            return ResponseEntity.badRequest()
                    .body("No existe la tienda desde la cual esta intentando enviar la orden");
        }
    }

    @RequestMapping(value = "/pending", method = RequestMethod.GET)
    public ResponseEntity<?> pending(@RequestParam String collection_id,
            @RequestParam String collection_status,
            @RequestParam String payment_id, @RequestParam String status, @RequestParam String external_reference,
            @RequestParam String merchant_order_id, @RequestParam String preference_id) {
        return ResponseEntity.ok("pending pay");
    }

    @RequestMapping(value = "failure", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> failure(@RequestBody MercadoPagoResponse model) throws Exception {

        return ResponseEntity.ok("pay failed");
    }

    @RequestMapping(value = "send-order/{orderCode}", method = RequestMethod.GET)
    public ResponseEntity<?> sendOrder(@PathVariable String orderCode) throws Exception {

        Optional<OrderInfo> orderInfo = orderInfoRepository.findByOriCode(orderCode);
        if (orderInfo.isPresent()) {
            // Set status 2 (payed)
            orderInfo.get().setOrsId(Long.parseLong("4"));
            orderInfo.get().setLastModifiedAt(new Date());

            // Send mail to the custom
            sendMail.singleAddress(getEmail(orderInfo.get().getPreguntas()), "Pedido enviado",
                    mailBody.orderDelivery(orderInfo.get().getOriCode(), orderInfo.get().getBrand().getName()));

        }
        return ResponseEntity.ok("Order enviada");
    }

    @RequestMapping(value = "check-mail/{orderCode}", method = RequestMethod.GET)
    public ResponseEntity<?> checkMail(@PathVariable String orderCode) throws Exception {

        Optional<OrderInfo> orderInfo = orderInfoRepository.findByOriCode(orderCode);
        if (orderInfo.isPresent()) {
            String email = getEmail(orderInfo.get().getPreguntas());
            if (email == null) {
                return ResponseEntity.badRequest()
                        .body("NoMail");
            }

        } else {
            return ResponseEntity.badRequest()
                    .body("No existe la orden");
        }
        return ResponseEntity.ok("Email Existente");
    }

    public String getEmail(List<Pregunta> preguntas) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getPregunta().equals("Email")) {
                return preguntas.get(i).getRespuesta();
            }
        }
        return null;
    }

}
