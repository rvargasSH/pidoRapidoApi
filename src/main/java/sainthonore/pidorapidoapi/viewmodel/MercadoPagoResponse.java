package sainthonore.pidorapidoapi.viewmodel;

import lombok.Data;

@Data
public class MercadoPagoResponse {
    String payment_id;
    String status;
    String external_reference;
    String comerciante_order_id;
}
