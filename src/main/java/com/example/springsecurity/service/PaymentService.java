package com.example.springsecurity.service;

import com.example.springsecurity.dto.CreatePayment;
import com.example.springsecurity.models.Appointment;
import com.example.springsecurity.models.Payment;
import com.example.springsecurity.models.Test;
import com.example.springsecurity.repository.AppointmentRepository;
import com.example.springsecurity.repository.PaymentRepository;
import com.example.springsecurity.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;


    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    TestRepository testRepository ;


    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;


    }

    public Long createPaymentIntent(Long id) {

        List<Test> tests = testRepository.findAll();

        Optional<Appointment> appointment = appointmentRepository.findById(id);
        Long price= (long) 0l;
        for (Test test : tests) {
            if ( test.getTestName().equals(appointment.get().getTestType()))
            {
                price = test.getTestPrice();
            }
        }

        return price;
    }

    public Payment createPayment(CreatePayment createPayment) {
        Payment payment = new Payment();
        payment.setDate(new Date());
        payment.setAppointmentId(createPayment.getAppointmentId());
        payment.setStatus("successful");
        payment.setUsername(createPayment.getUsername());
        payment.setEmail(createPayment.getEmail());
        List<Test> tests = testRepository.findAll();

        Optional<Appointment> appointment = appointmentRepository.findById(payment.getAppointmentId());
        Long price= (long) 0l;
        for (Test test : tests) {
          if ( test.getTestName().equals(appointment.get().getTestType()))
            {
                 price = test.getTestPrice();
            }
        }

        payment.setTotalSales(price);

        if (appointment.isPresent())
        {
            appointment.get().setStatus("in-progress");
            appointmentService.updateAppointment(appointment.get().getId(), appointment.get());
            return paymentRepository.save(payment);
        }
        return null;
    }
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    public List<Payment> getPaymentByEmail(String email) {
        return paymentRepository.findByEmail(email);
    }

    // Add other service methods as needed

    public List<Object[]> getMonthlySales() {
        return paymentRepository.getMonthlySales();
    }

    public List<Object[]> getYearlySales() {
        return paymentRepository.getYearlySales();
    }
    public Double getTodaySales() {
        return paymentRepository.getTodaySales();
    }
    public Double getTotalSales() {
        return paymentRepository.getTotalSales();
    }

}
