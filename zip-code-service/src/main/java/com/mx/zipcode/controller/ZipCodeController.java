package com.mx.zipcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zipcode.model.ZipCode;
import com.mx.zipcode.service.ZipCodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/zip-codes")
public class ZipCodeController {

	@Autowired
	private ZipCodeService zipCodeService;

	@GetMapping("/{code}")
	public ZipCode index(@PathVariable String code) {
		log.info(" --- index --- ");
		log.info("code :" + code);

		ZipCode out = zipCodeService.findZipCode(code);
		return out;
	}

}
