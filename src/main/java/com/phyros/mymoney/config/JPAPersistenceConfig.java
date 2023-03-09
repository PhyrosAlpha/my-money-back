package com.phyros.mymoney.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.phyros.mymoney.repository")
@EntityScan(basePackages = { "com.phyros.mymoney.entity" })
public class JPAPersistenceConfig {

}
