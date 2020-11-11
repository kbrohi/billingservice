package com.kaleem.billingservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kaleem.billingservice.vo.ItemQuantity;

@Service
public class BillingService {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String fileName = "/billing_data.json";

	public ObjectNode calculateBill(String requestBody) throws IOException {
		double finalAmount = 0.0;
		ObjectNode nodeObject = objectMapper.createObjectNode();
		if (!StringUtils.isEmpty(requestBody)) {
			List<Double> rateList = new ArrayList<>();
			List<ItemQuantity> inputData = objectMapper.readValue(requestBody, new TypeReference<List<ItemQuantity>>() {
			});
			// loading data from file
			JsonNode loadData = getItemsData();
			boolean calculateTip = false;
			for (ItemQuantity itemQuantity : inputData) {
				for (JsonNode node : loadData) {
					if (node.get("id").asInt() == itemQuantity.getId().intValue()) {
						double value = node.get("price").asDouble() * itemQuantity.getQuantity();
						rateList.add(value);
						// calculate tip logic
						if (itemQuantity.getId().intValue() == 3 || itemQuantity.getId().intValue() == 4) {
							calculateTip = true;
						}
						break;
					}
				}
			}
			if (!CollectionUtils.isEmpty(rateList)) {
				finalAmount = rateList.stream().mapToDouble(o -> o.doubleValue()).sum();
				nodeObject.put("Total Amount", finalAmount);
				if (calculateTip) {
					// 10% of final amount
					double tip = finalAmount * 10 / 100;
					nodeObject.put("Tip", tip);
					finalAmount += tip;
					nodeObject.put("Grand Total ", finalAmount);
				}
			}
		}
		return nodeObject;
	}

	private JsonNode getItemsData() throws IOException {
		return objectMapper.readTree(BillingService.class.getResourceAsStream(fileName));
	}

}
