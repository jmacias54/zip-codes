package com.mx.zipcode.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZipCode {

	@JsonProperty("zip_code")
	private String zipCode;
	private String municipality;
	private String locality;
	@JsonProperty("federal_entity")
	private String federalEntity;
	private List<Settlement> settlements;

}
