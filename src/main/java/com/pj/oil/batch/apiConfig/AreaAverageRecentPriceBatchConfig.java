package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.process.AreaAverageRecentPriceProcess;
import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import com.pj.oil.gasStation.repository.jpa.AreaAverageRecentPriceRepository;
import com.pj.oil.gasStationApi.GasStationApiService;
import com.pj.oil.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class AreaAverageRecentPriceBatchConfig {

    private static String[] areas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "14", "15", "16", "17", "18", "19"};
    private final String yesterday;
    private final PlatformTransactionManager platformTransactionManager;
    private final AreaAverageRecentPriceRepository repository;
    private final JobRepository jobRepository;
    private final GasStationApiService gasStationApiService;
    private final DateUtil dateUtil;

    public AreaAverageRecentPriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            AreaAverageRecentPriceRepository repository,
            GasStationApiService gasStationApiService,
            DateUtil dateUtil
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.repository = repository;
        this.gasStationApiService = gasStationApiService;
        this.dateUtil = dateUtil;
        this.yesterday = dateUtil.getYesterdayDateString();
    }

    @Bean(name = "areaAverageRecentPriceReader")
    @JobScope
    public ItemReader<AreaAverageRecentPrice> reader() {
        return new ItemReader<AreaAverageRecentPrice>() {
            private Iterator<AreaAverageRecentPrice> dataIterator;

            {
                List<AreaAverageRecentPrice> data = new ArrayList<>();
                for (String area : areas) {
                    List<AreaAverageRecentPrice> areaLowTop20ProdPrice = gasStationApiService.getAreaAvgRecentNDateAllProdPrice(area, yesterday);
                    data.addAll(areaLowTop20ProdPrice);
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


    @Bean(name = "areaAverageRecentPriceProcess")
    public ItemProcessor<AreaAverageRecentPrice, AreaAverageRecentPrice> processor() {
        return new AreaAverageRecentPriceProcess();
    }

    @Bean(name = "areaAverageRecentPriceWriter")
    public ItemWriter<AreaAverageRecentPrice> writer() {

        RepositoryItemWriter<AreaAverageRecentPrice> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("areaAverageRecentPriceSave");
        return writer;
    }

    @Bean(name = "areaAverageRecentPriceImportStep")
    public Step importStep() {
        return new StepBuilder("areaAverageRecentPriceImport", jobRepository)
                .<AreaAverageRecentPrice, AreaAverageRecentPrice>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(reader())
                .processor(processor())
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
                .build();
    }
}

