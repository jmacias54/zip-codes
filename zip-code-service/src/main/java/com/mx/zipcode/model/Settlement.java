package com.mx.zipcode.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {

	private String name;

	@JsonProperty("settlement_type")
	private String settlementType;

}
