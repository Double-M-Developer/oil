package com.pj.oil.batch;


import org.springframework.batch.core.DefaultJobKeyGenerator;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.DefaultExecutionContextSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchInfrastructureConfig {

    @Bean(name = "gasStationJobRepository")
    public JobRepository jobRepository(@Qualifier("gasStationDataSource") DataSource dataSource,
                                       @Qualifier("gasStationTransactionManager") PlatformTransactionManager transactionManager) {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();

        // JdbcOperations 설정
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        factory.setJdbcOperations(jdbcTemplate);

        // ConversionService 설정
        ConfigurableConversionService conversionService = new DefaultConversionService();
        factory.setConversionService(conversionService);

        // Serializer 설정
        ExecutionContextSerializer serializer = new DefaultExecutionContextSerializer();
        factory.setSerializer(serializer);

        // incrementerFactory 설정
        DefaultDataFieldMaxValueIncrementerFactory incrementerFactory =
                new DefaultDataFieldMaxValueIncrementerFactory(dataSource);
        factory.setIncrementerFactory(incrementerFactory);


        // jobKeyGenerator 설정
        factory.setJobKeyGenerator(new DefaultJobKeyGenerator());

        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_");


        // 데이터베이스 유형을 설정
        factory.setDatabaseType(DatabaseType.MARIADB.name());

        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new RuntimeException("JobRepository 생성 중 오류 발생", e);
        }
    }
}
