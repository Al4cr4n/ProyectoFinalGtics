package com.example.telexpress.dao;

//import com.example.telexpress.dto.PaymentResponseDTO;

import com.example.telexpress.dto.PaymentResponseDTO;
import com.example.telexpress.dto.ProductoOrdenDTO;


import com.example.telexpress.entity.ProductoOrdenes;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository //repository es una especializacion de component, se escoge repository porque permite el manejo de excepciones en el acceso a datos con mayor capacidad
@Service
public class StripePaymentDAO {

    @Value("${stripe.api.key}")
    private String accessToken;

    @PostConstruct
    public void init() {
        Stripe.apiKey = accessToken;
    }

    //con dto
    public PaymentIntent createPaymentIntent(List<ProductoOrdenes> productosEnOrden, String currency) {
        try {
            //inicializa acceso mediante el token para las solicitudes
            Stripe.apiKey = accessToken;
            long totalAmount = 0;
            Map<String, String> metadata = new HashMap<>();
            //configuracion de la solicitud
            //StringBuilder description = new StringBuilder("Detalles de la Orden:\n");

            for (int i =0 ; 1< productosEnOrden.size(); i++) {
                ProductoOrdenes productoenlaorden = productosEnOrden.get(i);
                String nombreProducto = productoenlaorden.getProducto().getNombreProducto();
                Integer cantidad = productoenlaorden.getCantidadxproducto();
                Double precioUnitario = productoenlaorden.getProducto().getPrecio();
                Double subtotalProducto = precioUnitario * cantidad;

                totalAmount += subtotalProducto * 100; // Convertir a la moneda más pequeña (centavos)

                //agregando detalles a la metadata
                metadata.put("nombreProducto_"+ i , nombreProducto);
                metadata.put("cantidaxproducto_"+ i , String.valueOf(cantidad));
                metadata.put("precioUnitario_"+i,String.format("%.2f",precioUnitario));
                metadata.put("subtotalProducto_"+i,String.format("%.2f",subtotalProducto));
            }

            PaymentIntentCreateParams.Builder paramsbuilder = PaymentIntentCreateParams.builder()
                    .setAmount(totalAmount)
                    .setCurrency(currency)
                    .addPaymentMethodType("card");

            // Agregar metadata al builder
            for (Map.Entry<String, String> entry : metadata.entrySet()) {
                paramsbuilder.putMetadata(entry.getKey(), entry.getValue());
            }

            PaymentIntentCreateParams params = paramsbuilder.build();
            return PaymentIntent.create(params);
        }catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el PaymentIntent en Stripe: " + e.getMessage());
        }
    }

    public void crearPago(PaymentResponseDTO pagoRequest) throws StripeException{

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) (pagoRequest.getMonto() * 100))
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .putMetadata("ordenId", pagoRequest.getOrdenId().toString())
                .build();
        //PaymentIntent.create(createParams);

        // Crear el PaymentIntent
        PaymentIntent paymentIntent = PaymentIntent.create(createParams);
        System.out.println("PaymentIntent creado con ID: " + paymentIntent.getId());
        // Confirmar el PaymentIntent
        PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                .setPaymentMethod("pm_card_visa") // Asegúrate de tener un método de pago válido
                .build();

        paymentIntent = paymentIntent.confirm(confirmParams);
        System.out.println("PaymentIntent confirmado. Estado actual: " + paymentIntent.getStatus());
    }
}

/* sin dto
public PaymentResponseDTO createPayment(List<ProductoOrdenes> productosEnOrden) {
        try {
            //inicializa acceso mediante el token para las solicitudes
            MercadoPago.SDK.setAccessToken(accessToken);
            //configuracion de la solicitud
            Preference preference = new Preference();

            for (ProductoOrdenes productoOrden : productosEnOrden) {
                Item item = new Item();
                item.setTitle(productoOrden.getProducto().getNombreProducto())
                        .setQuantity(productoOrden.getCantidadxproducto())
                        .setUnitPrice(productoOrden.getProducto().getPrecio().floatValue());
                preference.appendItem(item);
            }
            preference.save();
            return  new PaymentResponseDTO(preference.getId(), preference.getInitPoint());
        }catch (MPException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            throw new RuntimeException("error al crear el pago en mercadopago");
        }
    }
 */