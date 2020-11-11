package com.kaleem.billingservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kaleem.billingservice.service.BillingService;

@RestController
@RequestMapping("/v1/billing")
public class BillingController {

	@Autowired
	BillingService billingService;

	@PostMapping
	public ResponseEntity<?> calculateBill(@RequestBody String requestBody) throws IOException {
		return new ResponseEntity<ObjectNode>(billingService.calculateBill(requestBody), HttpStatus.OK);
	}

}
