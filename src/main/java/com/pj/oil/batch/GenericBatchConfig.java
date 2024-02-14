//package com.pj.oil.batch;
//
//import com.pj.oil.gasStation.GasStationBaseService;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.*;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.dao.DataAccessException;
//import org.springframework.retry.backoff.ExponentialBackOffPolicy;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@EnableBatchProcessing(
//        dataSourceRef = "gasStationDataSource",
//        transactionManagerRef = "gasStationTransactionManager")
//public abstract class GenericBatchConfig<T> {
//    public GenericBatchConfig(@Qualifier("gasStationJobRepository") JobRepository jobRepository,
//                              @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
//                              GasStationBaseService gasStationBaseService
//    ) {
//        this.jobRepository = jobRepository;
//        this.platformTransactionManager = platformTransactionManager;
//        this.gasStationBaseService = gasStationBaseService;
//    }
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager platformTransactionManager;
//    protected final GasStationBaseService gasStationBaseService;
//
//    protected abstract GasStationBaseService getGasStationBaseService();
//
//    protected abstract ItemReader<T> getItemReader();
//
//    protected abstract ItemProcessor<T, T> getItemProcessor();
//
//    protected abstract ItemWriter<T> getItemWriter();
//
//    protected abstract String getJobName();
//
//    protected abstract String getStepName();
//
//    protected abstract int getChunkSize();
//
//    @Bean
//    public GasStationBaseService baseService() {
//        return gasStationBaseService;
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        int concurrencyLimit = 10;
//        asyncTaskExecutor.setConcurrencyLimit(concurrencyLimit); // 비동기 작업 수 설정, -1은 동시성 제한 없는 것
//        return asyncTaskExecutor;
//    }
//
//    @Bean
//    public ItemReader<T> itemReader() {
//        return getItemReader();
//    }
//
//    @Bean
//    public ItemProcessor<T, T> itemProcessor() {
//        return getItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<T> itemWriter() {
//        return getItemWriter();
//    }
//
//    @Bean
//    public Step step() {
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(1000); // 초기 지연시간 1초
//        backOffPolicy.setMultiplier(2); // 다음 지연시간은 이전 지연시간의 2배
//        backOffPolicy.setMaxInterval(30000); // 최대 지연시간 30초
//        return new StepBuilder(getStepName(), jobRepository)
//                .<T, T>chunk(getChunkSize(), platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
//                .reader(itemReader())
//                .processor(itemProcessor())
//                .writer(getItemWriter())
//                .transactionManager(platformTransactionManager)
//                .taskExecutor(taskExecutor())
//                .faultTolerant()
//                .retryLimit(3)
//                .retry(DataAccessException.class)
//                .backOffPolicy(backOffPolicy)
//                .build();
//    }
//
//    @Bean
//    public Job job() {
//        return new JobBuilder(getJobName(), jobRepository)
//                .start(step())
//                .build();
//    }
//}
