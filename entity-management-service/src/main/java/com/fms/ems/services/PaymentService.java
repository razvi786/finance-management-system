package com.fms.ems.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fms.common.entity.Payment;
import com.fms.common.ui.responses.PaymentsList;

@Service
public class PaymentService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.pms}")
	private String pmsEndpoint;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private ProjectService projectService;

	public PaymentsList getAllPayments() {
		return restTemplate.getForObject(pmsEndpoint + "/payments", PaymentsList.class);
	}

	public Payment getPaymentByPaymentUuid(UUID paymentUuid) {
		return restTemplate.getForObject(pmsEndpoint + "/payment/" + paymentUuid, Payment.class);
	}

	public ResponseEntity<Payment> updatePayment(String paymentUuid, Payment payment) {
		return restTemplate.exchange(pmsEndpoint + "/payment/" + paymentUuid, HttpMethod.PUT,
				new HttpEntity<Payment>(payment), Payment.class);
	}

	public ResponseEntity<Payment> createPayment(Payment payment) {
		return restTemplate.exchange(pmsEndpoint + "/payment", HttpMethod.POST, new HttpEntity<Payment>(payment),
				Payment.class);
	}

	public PaymentsList populateMetadata(PaymentsList payments) {
		for (Payment payment : payments) {
			String vendorName = vendorService.getVendorNameByVendorId(payment.getVendorId());
			String projectName = projectService.getProjectNameByProjectId(payment.getProjectId());
			payment.setVendorName(vendorName);
			payment.setProjectName(projectName);
		}
		return payments;
	}

	public Payment populateMetadata(Payment payment) {
		String vendorName = vendorService.getVendorNameByVendorId(payment.getVendorId());
		String projectName = projectService.getProjectNameByProjectId(payment.getProjectId());
		payment.setVendorName(vendorName);
		payment.setProjectName(projectName);
		return payment;
	}

}
