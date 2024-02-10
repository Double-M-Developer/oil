package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.process.AverageAllPriceProcess;
import com.pj.oil.gasStation.entity.maria.AverageAllPrice;
import com.pj.oil.gasStation.repository.jpa.AverageAllPriceRepository;
import com.pj.oil.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class AverageAllPriceBatchConfig {

    private static String[] areas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "14", "15", "16", "17", "18", "19"};
    private static String date = DateUtil.getYesterdayDateString();
    private final PlatformTransactionManager platformTransactionManager;
    private final AverageAllPriceRepository repository;
    private final JobRepository jobRepository;

    public AverageAllPriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            AverageAllPriceRepository repository
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.repository = repository;
        this.jobRepository = jobRepository;
    }

    @Bean(name = "averageAllPriceReader")
    public ItemReader<AverageAllPrice> reader() {

        return new ItemReader<AverageAllPrice>() {
            @Override
            public AverageAllPrice read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                return null;
            }
        };
    }

    @Bean(name = "averageAllPriceProcess")
    public ItemProcessor<AverageAllPrice, AverageAllPrice> processor() {
        return new AverageAllPriceProcess();
    }

    @Bean(name = "averageAllPriceWriter")
    public ItemWriter<AverageAllPrice> writer() {

        RepositoryItemWriter<AverageAllPrice> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("averageAllPriceSave");
        return writer;
    }

    @Bean(name = "averageAllPriceImportStep")
    public Step importStep() {
        return new StepBuilder("averageAllPriceImport", jobRepository)
                .<AverageAllPrice, AverageAllPrice>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
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
    @Bean(name = "averageAllPriceJob")
    public Job runJob() {
        return new JobBuilder("importAverageAllPrice", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .build();
    }
}
