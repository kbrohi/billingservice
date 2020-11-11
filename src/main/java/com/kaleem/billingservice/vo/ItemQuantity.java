package com.kaleem.billingservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemQuantity {

	private Integer id;
	private String name;
	private Integer quantity;

}
