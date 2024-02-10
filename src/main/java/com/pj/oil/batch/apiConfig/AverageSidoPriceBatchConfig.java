package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.process.AverageSidoPriceProcess;
import com.pj.oil.gasStation.entity.maria.AverageSidoPrice;
import com.pj.oil.gasStation.repository.jpa.AverageSidoPriceRepository;
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
public class AverageSidoPriceBatchConfig {

    private static String[] areas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "14", "15", "16", "17", "18", "19"};
    private static String date = DateUtil.getYesterdayDateString();
    private final PlatformTransactionManager platformTransactionManager;
    private final AverageSidoPriceRepository repository;
    private final JobRepository jobRepository;

    public AverageSidoPriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            AverageSidoPriceRepository repository
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.repository = repository;
    }
    @Bean(name = "averageSidoPriceReader")
    public ItemReader<AverageSidoPrice> reader() {

        return new ItemReader<AverageSidoPrice>() {
            @Override
            public AverageSidoPrice read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                return null;
            }
        };
    }

    @Bean(name = "averageSidoPriceProcess")
    public ItemProcessor<AverageSidoPrice, AverageSidoPrice> processor() {
        return new AverageSidoPriceProcess();
    }

    @Bean(name = "averageSidoPriceWriter")
    public ItemWriter<AverageSidoPrice> writer() {

        RepositoryItemWriter<AverageSidoPrice> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("averageSidoPriceSave");
        return writer;
    }

    @Bean(name = "averageSidoPriceImportStep")
    public Step importStep() {
        return new StepBuilder("averageSidoPriceImport", jobRepository)
                .<AverageSidoPrice, AverageSidoPrice>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
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
    @Bean(name = "averageSidoPriceJob")
    public Job runJob() {
        return new JobBuilder("importAverageSidoPrice", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .build();
    }
}
