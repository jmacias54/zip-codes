package com.mx.zipcode;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {
	@Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
          .driverClassName("com.mysql.cj.jdbc.Driver")
          .url("jdbc:mysql://34.67.164.31:3306/mexico_cp")
          .username("root")
          .password("HAf2ovdvwqcteMvv")
          .build();	
    }
}
