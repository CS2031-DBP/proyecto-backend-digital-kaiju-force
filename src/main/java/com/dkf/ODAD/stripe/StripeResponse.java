package com.dkf.ODAD.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StripeResponse {
    String paymentIntent;
    String ephemeralKey;
    String customer;
    String publishableKey;
}