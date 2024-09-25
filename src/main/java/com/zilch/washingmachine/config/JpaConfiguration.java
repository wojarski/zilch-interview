package com.zilch.washingmachine.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EntityScan(basePackages = { "com.zilch.washingmachine.persistence.model" })
@EnableJpaRepositories(value = "com.zilch.washingmachine.persistence")
public class JpaConfiguration {
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    //@Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        //dataSourceBuilder.driverClassName("org.h2.Driver");
        //dataSourceBuilder.url("jdbc:h2:mem:test");
        //dataSourceBuilder.username("SA");
        //dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

/*    @Bean
    public LocalSessionFactoryBean entityManagerFactory(final DataSource dataSource) {
        final LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("com.zilch.washingmachine.persistence.model");
        return localSessionFactoryBean;
    }*/
}
