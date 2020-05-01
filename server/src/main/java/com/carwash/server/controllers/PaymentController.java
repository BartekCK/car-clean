package com.carwash.server.controllers;


import com.carwash.server.dto.payments.PaymentDto;
import com.carwash.server.services.PaymentService;
import com.carwash.server.utilies.AuthMiner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/pay")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> payment(Authentication authentication, @RequestBody PaymentDto paymentDto) throws PayPalRESTException, JsonProcessingException {
        return paymentService.createPayment(AuthMiner.getUsername(authentication), paymentDto);
    }

    @GetMapping("/false")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.badRequest().body("Wystąpił błąd, spróbuj ponownie");
    }

    @GetMapping("/true")
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws PayPalRESTException, JsonProcessingException {
        return paymentService.executePayment(paymentId, payerId);
    }
}
