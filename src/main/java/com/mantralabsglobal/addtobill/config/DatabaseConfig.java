package com.mantralabsglobal.addtobill.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	
}
