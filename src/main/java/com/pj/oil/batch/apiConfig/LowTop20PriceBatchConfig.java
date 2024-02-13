package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.process.LowTop20PriceProcess;
import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import com.pj.oil.gasStation.repository.jpa.LowTop20PriceRepository;
import com.pj.oil.gasStationApi.GasStationApiService;
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
public class LowTop20PriceBatchConfig {

    private static String[] areas = {"01"};
//    private static String[] areas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "14", "15", "16", "17", "18", "19"};
    private static String[] prods = {"B027"};
//    private static String[] prods = {"B027", "D047", "B034", "C004", "K015"};

    private final PlatformTransactionManager platformTransactionManager;
    private final LowTop20PriceRepository repository;
    private final JobRepository jobRepository;
    private final GasStationApiService gasStationApiService;

    public LowTop20PriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            LowTop20PriceRepository repository,
            GasStationApiService gasStationApiService
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.repository = repository;
        this.gasStationApiService = gasStationApiService;
    }

    @Bean(name = "lowTop20PriceReader")
    @JobScope
    public ItemReader<LowTop20Price> reader() {
        return new ItemReader<LowTop20Price>() {
            private Iterator<LowTop20Price> dataIterator;
            {
                List<LowTop20Price> data = new ArrayList<>();
                for (String area : areas) {
                    for (String prod : prods) {
                        List<LowTop20Price> areaLowTop20ProdPrice = gasStationApiService.getAreaLowTop20ProdPrice(prod, area);
                        data.addAll(areaLowTop20ProdPrice);
                    }
                }
                this.dataIterator = data.iterator();
            }

            @Override
            public LowTop20Price read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (dataIterator.hasNext()) {
                    return dataIterator.next();
                } else {
                    return null; // 데이터의 끝에 도달했을 때 null을 반환하여 읽기 종료를 알림
                }
            }
        };
    }

    @Bean(name = "lowTop20PriceProcess")
    public ItemProcessor<LowTop20Price, LowTop20Price> processor() {
        return new LowTop20PriceProcess();
    }

    @Bean(name = "lowTop20PriceWriter")
    public ItemWriter<LowTop20Price> writer() {

        RepositoryItemWriter<LowTop20Price> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean(name = "lowTop20PriceImportStep")
    public Step importStep() {
        return new StepBuilder("lowTop20PriceImport", jobRepository)
                .<LowTop20Price, LowTop20Price>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
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
    @Bean(name = "lowTop20PriceJob")
    public Job runJob() {
        return new JobBuilder("importLowTop20Price", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .build();
    }
}
