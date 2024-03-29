package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.BeforeJobExecutionListener;
import com.pj.oil.batch.writer.LowTop20PriceWriter;
import com.pj.oil.gasStation.AreaRegistry;
import com.pj.oil.gasStation.ProductRegistry;
import com.pj.oil.gasStation.dto.LowTop20PriceDto;
import com.pj.oil.gasStationApi.GasStationApiService;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class LowTop20PriceBatchConfig {

    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;
    private final GasStationApiService gasStationApiService;
    private final BeforeJobExecutionListener beforeJobExecutionListener;
    private final JdbcTemplate jdbcTemplate;
    private final AreaRegistry areaRegistry;
    private final ProductRegistry productRegistry;

    public LowTop20PriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            GasStationApiService gasStationApiService,
            BeforeJobExecutionListener beforeJobExecutionListener,
            AreaRegistry areaRegistry,
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate,
            ProductRegistry productRegistry
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.gasStationApiService = gasStationApiService;
        this.beforeJobExecutionListener = beforeJobExecutionListener;
        this.jdbcTemplate = jdbcTemplate;
        this.areaRegistry = areaRegistry;
        this.productRegistry = productRegistry;
    }

    @Bean(name = "lowTop20PriceReader")
    @JobScope
    public ItemReader<LowTop20PriceDto> reader() {
        return new ItemReader<>() {
            private final Iterator<LowTop20PriceDto> dataIterator;
            {
                List<LowTop20PriceDto> data = new ArrayList<>();
                for (String area : areaRegistry.getAreaCodes()) {
                    for (String prod : productRegistry.getProductCodes()) {
                        List<LowTop20PriceDto> areaLowTop20ProdPrice = gasStationApiService.getAreaLowTop20ProdPrice(prod, area);
                        data.addAll(areaLowTop20ProdPrice);
                    }
                }
                this.dataIterator = data.iterator();
            }

            @Override
            public LowTop20PriceDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (dataIterator.hasNext()) {
                    return dataIterator.next();
                } else {
                    return null; // 데이터의 끝에 도달했을 때 null을 반환하여 읽기 종료를 알림
                }
            }
        };
    }

    @Bean(name = "lowTop20PriceWriter")
    public ItemWriter<LowTop20PriceDto> writer() {
        return new LowTop20PriceWriter(jdbcTemplate);
    }

    @Bean(name = "lowTop20PriceImportStep")
    public Step importStep() {
        return new StepBuilder("lowTop20PriceImport", jobRepository)
                .<LowTop20PriceDto, LowTop20PriceDto>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(reader())
                .writer(writer())
//                .taskExecutor(taskExecutor())
                .build();
    }

//    @Bean(name = "lowTop20PriceTaskExecutor")
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5); // 기본 스레드 풀 크기
//        executor.setMaxPoolSize(10); // 최대 스레드 풀 크기
//        executor.setQueueCapacity(25); // 큐 용량
//        executor.initialize();
//        return executor;
//    }
    @Bean(name = "lowTop20PriceJob")
    public Job runJob() {
        return new JobBuilder("importLowTop20Price", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .listener(beforeJobExecutionListener)
                .build();
    }
}
