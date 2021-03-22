package com.mx.zipcode.dao.impl;

public final class ZipCodeConstantsDAO {

	public static final String FIND_ZIP_CODE() {
		StringBuilder builder = new StringBuilder();

		builder.append(" SELECT  asentamiento.codigo_postal zipCode, ");
		builder.append(" 		municipio.name  municipality, ");
		builder.append("         ciudad.name  locality, ");
		builder.append("         estado.name federalEntity ");
		builder.append(" FROM mexico_cp.asentamiento asentamiento  ");
		builder.append(" INNER JOIN mexico_cp.municipio municipio ON asentamiento.fk_municipio = municipio.id ");
		builder.append(" INNER JOIN mexico_cp.estado estado ON municipio.fk_estado = estado.id ");
		builder.append(" INNER JOIN mexico_cp.ciudad ciudad ON asentamiento.fk_ciudad = ciudad.id ");
		builder.append(" WHERE asentamiento.codigo_postal = :code ");
		builder.append(" GROUP BY asentamiento.codigo_postal,municipio.name ,ciudad.name,estado.name  ");

		return builder.toString();

	}

	public static final String FIND_SETTLEMENTS() {
		StringBuilder builder = new StringBuilder();

		builder.append(" SELECT  asentamiento.name name , ");
		builder.append(" 		tipo.name settlementType ");
		builder.append(" FROM mexico_cp.asentamiento asentamiento  ");
		builder.append(" INNER JOIN mexico_cp.tipo_asentamiento tipo ON asentamiento.fk_tipo_asentamiento = tipo.id ");
		builder.append(" WHERE asentamiento.codigo_postal = :code ");

		return builder.toString();

	}

}
