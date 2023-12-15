package com.example.springsecurity.controllers;

import com.example.springsecurity.dto.CreatePayment;
import com.example.springsecurity.dto.CreatePaymentResponse;
import com.example.springsecurity.models.Payment;
import com.example.springsecurity.repository.PaymentRepository;
import com.example.springsecurity.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;



    //
    @GetMapping("/create-payment-intent/{appointmentId}")
    public CreatePaymentResponse createPaymentIntent(@PathVariable("appointmentId") Long appointmentId) throws StripeException {

            Long payment =  paymentService.createPaymentIntent(appointmentId);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) payment * 100)
                .setCurrency("pkr")
                .build();
        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new CreatePaymentResponse(paymentIntent.getClientSecret());
    }


    @PostMapping("/create")
    public CreatePaymentResponse createPaymen(@RequestBody CreatePayment createPayment) throws StripeException {

//        createPayment.setPrice(200000.89f);


        Payment payment =  paymentService.createPayment(createPayment);


        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) payment.getTotalSales() * 100)
                .setCurrency("pkr")
                .build();
        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new CreatePaymentResponse(paymentIntent.getClientSecret());

    }



    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/specificmail/{email}")
    public List<Payment> getPaymentByEmail(@PathVariable String email) {
        List<Payment> payment = paymentService.getPaymentByEmail(email);
        return payment;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/monthly-sales")
    public List<Object[]> getMonthlySales() {
        return paymentService.getMonthlySales();
    }

    @GetMapping("/yearly-sales")
    public List<Object[]> getYearlySales() {
        return paymentService.getYearlySales();
    }

    @GetMapping("/today-sales")
    public Double getTodaySales() {
        return paymentService.getTodaySales();
    }

    @GetMapping("/total-sales")
    public Double getTotalSales() {
        return paymentService.getTotalSales();
    }

    @GetMapping("/total-scuuessful-appointments")
    public Long getTotalAppoiintments() {
      List<Payment> payments =  paymentRepository.findAll();
      return (long) payments.size();

    }



}