package com.mx.zipcode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zipcode.dao.ZipCodeDAO;
import com.mx.zipcode.model.Settlement;
import com.mx.zipcode.model.ZipCode;
import com.mx.zipcode.service.ZipCodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZipCodeServiceImpl implements ZipCodeService {

	@Autowired
	private ZipCodeDAO zipCodeDAO;

	@Override
	public ZipCode findZipCode(String code) {
		log.info(" --- findZipCode --- ");
		log.info(" code : " + code);

		ZipCode out = zipCodeDAO.findZipCode(code);

		List<Settlement> settlements = zipCodeDAO.findSettlements(code);

		if (settlements != null && settlements.size() > 0)
			out.setSettlements(settlements);

		return out;
	}

}
