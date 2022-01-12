package sainthonore.pidorapidoapi.service;

import java.util.List;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.Preference.AutoReturn;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sainthonore.pidorapidoapi.viewmodel.ProductoVM;

@Service
public class MercadoPagoService {

    @Value("${urlNotification}")
    private String urlNotification;

    @Value("${accessToken}")
    private String accessToken;

    public String createPreference(List<ProductoVM> productos, String orderCode) throws MPException {
        MercadoPago.SDK.setAccessToken(accessToken);
        Preference preference = new Preference();

        // Crea un ítem en la preferencia

        for (int i = 0; i < productos.size(); i++) {
            Item item = new Item();
            item.setTitle(productos.get(i).getNombre())
                    .setQuantity(productos.get(i).getCantidad().intValue())
                    .setUnitPrice(Float.parseFloat(productos.get(i).getPrecio()));
            preference.appendItem(item);
        }

        BackUrls backUrls = new BackUrls(
                urlNotification + "/success",
                urlNotification + "/pending",
                urlNotification + "/failure");
        preference.setBackUrls(backUrls);
        preference.setBinaryMode(true);
        preference.setExternalReference(orderCode);
        preference.setAutoReturn(AutoReturn.all);
        // Crea un ítem en la preferencia
        preference.save();
        return preference.getSandboxInitPoint();

    }

}
