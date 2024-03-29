package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.BeforeJobExecutionListener;
import com.pj.oil.batch.writer.AverageRecentPriceWriter;
import com.pj.oil.gasStation.dto.AverageRecentPriceDto;
import com.pj.oil.gasStationApi.GasStationApiService;
import com.pj.oil.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Iterator;

@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class AverageRecentPriceBatchConfig {

    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;
    private final GasStationApiService gasStationApiService;
    private final BeforeJobExecutionListener beforeJobExecutionListener;
    private final JdbcTemplate jdbcTemplate;

    public AverageRecentPriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            GasStationApiService gasStationApiService,
            BeforeJobExecutionListener beforeJobExecutionListener,
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.gasStationApiService = gasStationApiService;
        this.beforeJobExecutionListener = beforeJobExecutionListener;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean(name = "averageRecentPriceReader")
    @JobScope
    public ItemReader<AverageRecentPriceDto> reader() {
        return new ItemReader<>() {
            String yesterday = DateUtil.getYesterdayDateString();
            private final Iterator<AverageRecentPriceDto> dataIterator = gasStationApiService.getAvgRecentNDateAllProdPrice(yesterday).iterator();
            @Override
            public AverageRecentPriceDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (dataIterator.hasNext()) {
                    return dataIterator.next();
                } else {
                    return null; // 데이터의 끝에 도달했을 때 null을 반환하여 읽기 종료를 알림
                }
            }
        };
    }

    @Bean(name = "averageRecentPriceWriter")
    public ItemWriter<AverageRecentPriceDto> writer() {
        return new AverageRecentPriceWriter(jdbcTemplate);
    }

    @Bean(name = "averageRecentPriceImportStep")
    public Step importStep() {
        return new StepBuilder("averageRecentPriceImport", jobRepository)
                .<AverageRecentPriceDto, AverageRecentPriceDto>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(reader())
//                .processor(processor())
                .writer(writer())
//                .taskExecutor(taskExecutor())
                .build();
    }

//    @Bean(name = "averageRecentPriceTaskExecutor")
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5); // 기본 스레드 풀 크기
//        executor.setMaxPoolSize(10); // 최대 스레드 풀 크기
//        executor.setQueueCapacity(25); // 큐 용량
//        executor.initialize();
//        return executor;
//    }
    @Bean(name = "averageRecentPriceJob")
    public Job runJob() {
        return new JobBuilder("importAverageRecentPrice", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .listener(beforeJobExecutionListener)
                .build();
    }
}

