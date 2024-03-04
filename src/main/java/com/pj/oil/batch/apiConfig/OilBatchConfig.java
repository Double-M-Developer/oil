package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.BeforeJobExecutionListener;
import com.pj.oil.batch.process.GasStationOilProcess;
import com.pj.oil.batch.writer.GasStationOilWriter;
import com.pj.oil.gasStation.entity.maria.GasStationOil;
import com.pj.oil.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class OilBatchConfig {

    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;
    private final String READER_PATH;
    private final BeforeJobExecutionListener beforeJobExecutionListener;
    private final JdbcTemplate jdbcTemplate;

    public OilBatchConfig(@Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
                          @Qualifier("gasStationJobRepository") JobRepository jobRepository,
                          BeforeJobExecutionListener beforeJobExecutionListener,
                          @Qualifier("gasStationJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.READER_PATH = "src/main/resources/csv/" + DateUtil.getTodayDateString() + "/" + DateUtil.getTodayDateString() + "-";
        this.beforeJobExecutionListener = beforeJobExecutionListener;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean(name = "oilReader")
    @StepScope
    public FlatFileItemReader<GasStationOil> reader() {
        FlatFileItemReader<GasStationOil> itemReader = new FlatFileItemReader<>();
        String path = READER_PATH + "basic-info-oil.csv";
        itemReader.setResource(new FileSystemResource(path)); // api 나 파일로부터 작업을 처리하도록 할 수 있음
        itemReader.setName("csvReader"); // itemReader 이름 설정
        itemReader.setEncoding("UTF-8");
        itemReader.setLinesToSkip(1); // 건너뛸 줄 설정
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean(name = "oilProcess")
    public GasStationOilProcess processor() {
        return new GasStationOilProcess();
    }


    private LineMapper<GasStationOil> lineMapper() {
        DefaultLineMapper<GasStationOil> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(","); // 데이터 쉼표로 구분
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("uniId", "area", "osName", "pollDivName", "newAddress"); // 요소의 열을 구분
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4); // csv 에서 특정 열을 선택
//        고유번호,지역,상호,상표,주소,전화번호,셀프여부, 품질인증주유소, 전산보고주유소
        BeanWrapperFieldSetMapper<GasStationOil> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(GasStationOil.class); // 파일을 객체로 변환할 수 있도록 도와주는 객체

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean(name = "oilWriter")
    public ItemWriter<GasStationOil> writer() {

        return new GasStationOilWriter(jdbcTemplate);
    }

    @Bean(name = "oilImportStep")
    public Step importStep() {
        return new StepBuilder("oilCsvImport", jobRepository)
                .<GasStationOil, GasStationOil>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "oilTaskExecutor")
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10); // 비동기 작업 수 설정, -1은 동시성 제한 없는 것
        return asyncTaskExecutor;
    }
    @Bean(name = "oilJob")
    public Job runJob() {
        return new JobBuilder("importOil", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .listener(beforeJobExecutionListener)
                .build();
    }
}
