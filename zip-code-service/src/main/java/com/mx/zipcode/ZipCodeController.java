package com.mx.zipcode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/zip-codes")
public class ZipCodeController {
	
	@GetMapping("/{code}")
	public String index(@PathVariable String code) {
		log.info(" --- index --- ");
		log.info("code :" + code);
		return code;
	}

}
