package com.mx.zipcode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mx.zipcode.service.ZipCodeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigSpring.class })
class ZipCodeServiceApplicationTests {

	@Autowired
	private ZipCodeService zipCodeService;

	@Test
	public void test() {

		assertThat(zipCodeService.findZipCode("62900")).isNotNull();
	}
}
