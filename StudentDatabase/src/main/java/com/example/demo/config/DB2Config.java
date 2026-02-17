package com.example.demo.config;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.demo.repos.db2",
    entityManagerFactoryRef = "db2EntityManagerFactory",
    transactionManagerRef = "db2TransactionManager"
)
public class DB2Config {


	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.db2")
	public HikariDataSource db2DataSource() {
	    return new HikariDataSource();
	}


	@Bean
	public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory() {

	    LocalContainerEntityManagerFactoryBean em =
	            new LocalContainerEntityManagerFactoryBean();

	    em.setDataSource(db2DataSource());
	    em.setPackagesToScan("com.example.demo.entities");

	    HibernateJpaVendorAdapter vendorAdapter =
	            new HibernateJpaVendorAdapter();
	    em.setJpaVendorAdapter(vendorAdapter);

	    Map<String, Object> props = new HashMap<>();
	    props.put("hibernate.hbm2ddl.auto", "update");
	    props.put("hibernate.show_sql", true);

	    em.setJpaPropertyMap(props);


	    return em;
	}



    @Bean
    public PlatformTransactionManager db2TransactionManager(
            @Qualifier("db2EntityManagerFactory")
            EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}


