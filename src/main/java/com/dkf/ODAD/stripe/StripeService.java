package com.dkf.ODAD.stripe;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${publishable.key}")
    private String publishableKey;

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private MedicoRepository medicoRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public StripeResponse generatePayment(StripeRequest request) throws Exception {
        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setEmail(request.getEmail())
                .build();
        Customer customer = Customer.create(customerParams);

        EphemeralKeyCreateParams ephemeralKeyParams = EphemeralKeyCreateParams.builder()
                .setStripeVersion("2024-06-20")
                .setCustomer(customer.getId())
                .build();
        EphemeralKey ephemeralKey = EphemeralKey.create(ephemeralKeyParams);

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new Exception("Medico no encontrado"));

        PaymentIntentCreateParams paymentIntentParams = PaymentIntentCreateParams.builder()
                .setAmount(medico.getPrecioInCents()) // Usar el precio del médico
                .setCurrency("PEN")
                .setCustomer(customer.getId())
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

        return new StripeResponse(paymentIntent.getClientSecret(), ephemeralKey.getSecret(), customer.getId(), publishableKey);
    }
}