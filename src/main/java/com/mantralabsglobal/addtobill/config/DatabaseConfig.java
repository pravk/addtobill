package com.mantralabsglobal.addtobill.config;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableMongoAuditing(auditorAwareRef="mongoAuditor")
@EnableMongoRepositories(basePackages="com.mantralabsglobal.addtobill")
public class DatabaseConfig {

	@Value(value="${spring.datasource.url}")
	private String sqlUrl;
	
	@Value(value="${spring.datasource.username}")
	private String userName;

	@Value(value="${spring.datasource.password}")
	private String password;

	@Value(value="${spring.datasource.driver-class-name}")
	private String driver;

	
	@Bean(name="lockDS")
    DataSource dsDB1() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(sqlUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }
	
	
	@Autowired
	private GenericConversionService genericConversionService;
	
	@PostConstruct
    public void longToDateConverter(){
		genericConversionService.addConverter(new Converter<Long,Date>(){

			@Override
			public Date convert(Long source) {
				return new Date(source);
			}
        	
        });
    }
	
}
