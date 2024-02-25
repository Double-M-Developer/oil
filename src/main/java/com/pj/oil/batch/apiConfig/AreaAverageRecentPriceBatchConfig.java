package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.BeforeJobExecutionListener;
import com.pj.oil.batch.writer.AreaAverageRecentPriceWriter;
import com.pj.oil.gasStation.AreaRegistry;
import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager"
)
public class AreaAverageRecentPriceBatchConfig {

    private final String yesterday;
    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;
    private final GasStationApiService gasStationApiService;
    private final DateUtil dateUtil;
    private final BeforeJobExecutionListener beforeJobExecutionListener;
    private final JdbcTemplate jdbcTemplate;
    private final AreaRegistry areaRegistry;

    public AreaAverageRecentPriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            GasStationApiService gasStationApiService,
            DateUtil dateUtil,
            BeforeJobExecutionListener beforeJobExecutionListener,
            @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate,
            AreaRegistry areaRegistry
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.gasStationApiService = gasStationApiService;
        this.dateUtil = dateUtil;
        this.yesterday = dateUtil.getYesterdayDateString();
        this.beforeJobExecutionListener = beforeJobExecutionListener;
        this.jdbcTemplate = jdbcTemplate;
        this.areaRegistry = areaRegistry;
    }

    @Bean(name = "areaAverageRecentPriceReader")
    @JobScope
    public ItemReader<AreaAverageRecentPrice> reader() {
        return new ItemReader<>() {
            private final Iterator<AreaAverageRecentPrice> dataIterator;

            {
                List<AreaAverageRecentPrice> data = new ArrayList<>();
                for (String area : areaRegistry.getAreaCodes()) {
                    List<AreaAverageRecentPrice> areaAverageRecentPrice = gasStationApiService.getAreaAvgRecentNDateAllProdPrice(area, yesterday);
                    data.addAll(areaAverageRecentPrice);
                }
                this.dataIterator = data.iterator();
            }

            @Override
            public AreaAverageRecentPrice read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (dataIterator.hasNext()) {
                    return dataIterator.next();
                } else {
                    return null; // 데이터의 끝에 도달했을 때 null을 반환하여 읽기 종료를 알림
                }
            }
        };
    }
    @Bean(name = "areaAverageRecentPriceWriter")
    public ItemWriter<AreaAverageRecentPrice> writer() {
        return new AreaAverageRecentPriceWriter(jdbcTemplate);
    }

    @Bean(name = "areaAverageRecentPriceImportStep")
    public Step importStep() {
        return new StepBuilder("areaAverageRecentPriceImport", jobRepository)
                .<AreaAverageRecentPrice, AreaAverageRecentPrice>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(reader())
//                .processor(processor())
                .writer(writer())
//                .taskExecutor(taskExecutor())
                .build();
    }

//    @Bean
//    public TaskExecutor taskExecutor() {
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        asyncTaskExecutor.setConcurrencyLimit(10); // 비동기 작업 수 설정, -1은 동시성 제한 없는 것
//        return asyncTaskExecutor;
//    }
    @Bean(name = "areaAverageRecentPriceJob")
    public Job runJob() {
        return new JobBuilder("importAreaAverageRecentPrice", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .listener(beforeJobExecutionListener)
                .build();
    }
}

