package com.dkf.ODAD.stripe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe")
public class StripeController {
    @Autowired
    StripeService service;

    @PostMapping("/payment-sheet")
    public ResponseEntity<StripeResponse> payment(@RequestBody StripeRequest request) throws Exception {
        return ResponseEntity.ok(service.generatePayment(request));
    }
}