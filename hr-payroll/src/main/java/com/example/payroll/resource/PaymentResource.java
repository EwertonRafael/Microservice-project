package com.example.payroll.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payroll.entities.Payment;
import com.example.payroll.services.PaymentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {
	
	@Autowired
	private PaymentService service;
	
	@CircuitBreaker(fallbackMethod = "getPaymentAlternative", name = "paymentFallback")
	@GetMapping(value = "/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days){
		Payment payment = service.getPayment(workerId, days);
		return ResponseEntity.ok(payment);
	}
	
	public ResponseEntity<Payment> getPaymentAlternative( Long workerId,  Integer days, Throwable t){
		Payment payment = new Payment("Brann", 400.0, days);
		return ResponseEntity.ok(payment);

	}

}
