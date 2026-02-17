package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.HashMap;
import java.util.Map;


import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.demo.repos.db1",
    entityManagerFactoryRef = "db1EntityManagerFactory",
    transactionManagerRef = "db1TransactionManager"
)
public class DB1Config {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	public HikariDataSource db1DataSource() {
	    return new HikariDataSource();
	}



	@Bean
	public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory() {

	    LocalContainerEntityManagerFactoryBean em =
	            new LocalContainerEntityManagerFactoryBean();

	    em.setDataSource(db1DataSource());
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
    public PlatformTransactionManager db1TransactionManager(
            @Qualifier("db1EntityManagerFactory")
            EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

