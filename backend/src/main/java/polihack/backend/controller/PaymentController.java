package polihack.backend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.PaymentRequest;
import polihack.backend.model.enums.Currency;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${client.base.url}")
    private String clientBaseUrl;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) {
        Stripe.apiKey = stripeApiKey;

        if (paymentRequest.getAmount() == null || paymentRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Default la RON daca nu e specificat
        Currency currencyEnum = paymentRequest.getCurrency() != null ? paymentRequest.getCurrency() : Currency.RON;

        String stripeCurrency = currencyEnum.name().toLowerCase();

        // Calculam suma in cea mai mica unitate (bani/centi)
        long amountInCents = paymentRequest.getAmount() * 100;

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(clientBaseUrl + "/success")
                    .setCancelUrl(clientBaseUrl + "/donate")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(stripeCurrency) // Folosim string-ul convertit
                                                    .setUnitAmount(amountInCents)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Donatie Polihack")
                                                                    .setDescription("Sustinere (" + currencyEnum.name() + ")")
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            Session session = Session.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("checkoutUrl", session.getUrl());

            return ResponseEntity.ok(responseData);

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}