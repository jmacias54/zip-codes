package com.mx.zipcode.dao;

import java.util.List;

import com.mx.zipcode.model.Settlement;
import com.mx.zipcode.model.ZipCode;

public interface ZipCodeDAO {

	ZipCode findZipCode(String code);

	List<Settlement> findSettlements(String code);

}
