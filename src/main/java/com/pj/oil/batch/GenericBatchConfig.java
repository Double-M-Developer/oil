package com.pj.oil.batch;

import com.pj.oil.cache.CacheService;
import com.pj.oil.gasStationApi.GasStationApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@RequiredArgsConstructor
public abstract class GenericBatchConfig<T, U> {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;
    private final GasStationApiService gasStationApiService;
    private final CacheService cacheService;
    protected abstract ItemReader<T> getItemReader(GasStationApiService service);
    protected abstract ItemProcessor<T, U> getItemProcessor(CacheService cacheService);
    protected abstract JdbcBatchItemWriter<U> getJdbcBatchItemWriter();
    protected abstract String getWriterSql();
    protected abstract String getJobName();
    protected abstract String getStepName();
    protected abstract int getChunkSize();

    @Bean
    public GasStationApiService apiService() {
        return gasStationApiService;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        int concurrencyLimit = 10;
        asyncTaskExecutor.setConcurrencyLimit(concurrencyLimit); // 비동기 작업 수 설정, -1은 동시성 제한 없는 것
        return asyncTaskExecutor;
    }
    @Bean
    public ItemReader<T> itemReader() {
        return getItemReader(gasStationApiService);
    }

    @Bean
    public ItemProcessor<T,U> itemProcessor() {
        return getItemProcessor(cacheService);
    }

    @Bean
    public JdbcBatchItemWriter<U> jdbcBatchItemWriter() {
        JdbcBatchItemWriter<U> writer = getJdbcBatchItemWriter();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql(getWriterSql());
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Step step() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 초기 지연시간 1초
        backOffPolicy.setMultiplier(2); // 다음 지연시간은 이전 지연시간의 2배
        backOffPolicy.setMaxInterval(30000); // 최대 지연시간 30초
        return new StepBuilder(getStepName(), jobRepository)
                .<T, U>chunk(getChunkSize(), platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(jdbcBatchItemWriter())
                .taskExecutor(taskExecutor())
                .faultTolerant()
                .retryLimit(3)
                .retry(DataAccessException.class)
                .backOffPolicy(backOffPolicy)
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder(getJobName(), jobRepository)
                .start(step())
                .build();
    }
}
