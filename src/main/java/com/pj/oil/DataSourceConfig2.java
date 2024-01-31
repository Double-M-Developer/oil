package com.pj.oil;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.pj.oil.memberPost",
        entityManagerFactoryRef = "memberPostEntityManagerFactory"
)
@EntityScan("com.pj.oil.memberPost")
public class DataSourceConfig2 {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.member-post")
    public DataSource memberPostDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "memberPostEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder memberPostEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

    @Bean(name = "memberPostEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean memberPostEntityManagerFactory(
            @Qualifier("memberPostEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("memberPostDataSource") DataSource dataSource) {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.format_sql", true);

        return builder
                .dataSource(dataSource)
                .properties(jpaProperties)
                .packages("com.pj.oil.memberPost")
//                .persistenceUnit("gasStationPersistenceUnit")
                .build();
    }

    @Bean
    public PlatformTransactionManager memberPostTransactionManager(
            @Qualifier("memberPostEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}


