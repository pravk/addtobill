package com.mantralabsglobal.addtobill.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableMongoAuditing(auditorAwareRef="mongoAuditor")
@EnableMongoRepositories(basePackages="com.mantralabsglobal.addtobill")
public class DatabaseConfig {

	@Bean(name="lockDS")
    DataSource dsDB1() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
	
}