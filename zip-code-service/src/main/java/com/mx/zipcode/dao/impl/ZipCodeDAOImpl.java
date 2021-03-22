package com.mx.zipcode.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mx.zipcode.dao.ZipCodeDAO;
import com.mx.zipcode.model.Settlement;
import com.mx.zipcode.model.ZipCode;
import com.mx.zipcode.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ZipCodeDAOImpl implements ZipCodeDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public ZipCode findZipCode(String code) {
		log.info(" --- findZipCode --- ");
		log.info(" code : "+code);


		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource("code", code);

			ZipCode out = namedParameterJdbcTemplate.queryForObject(ZipCodeConstantsDAO.FIND_ZIP_CODE(), parameters,
					new BeanPropertyRowMapper<ZipCode>(ZipCode.class));

			return out;
		} catch (EmptyResultDataAccessException emptyEx) {
			log.error(Constants.EXCEPTION_EMPTY_RESULT, emptyEx);
			return null;
		}
	}

	@Override
	public List<Settlement> findSettlements(String code) {
		log.info(" --- findSettlements --- ");
		log.info(" code : "+code);


		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource("code", code);

			List<Settlement> lista = namedParameterJdbcTemplate.query(ZipCodeConstantsDAO.FIND_SETTLEMENTS(), parameters,
					new BeanPropertyRowMapper<Settlement>(Settlement.class));

			return lista;
		} catch (EmptyResultDataAccessException emptyEx) {
			log.error(Constants.EXCEPTION_EMPTY_RESULT, emptyEx);
			return null;
		}
	}

}
