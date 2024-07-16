package com.dkf.ODAD.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequest {
    String email;
    Long medicoId; // Identificador del médico
}