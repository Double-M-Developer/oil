package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.process.GasStationProcess;
import com.pj.oil.gasStation.entity.maria.GasStation;
import com.pj.oil.gasStation.repository.maria.GasStationRepository;
import com.pj.oil.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class LpgBatchConfig {

    private final PlatformTransactionManager platformTransactionManager;
    private final GasStationRepository repository;
    private final JobRepository jobRepository;
    private final DateUtil dateUtil;
    private final String READER_PATH;

    public LpgBatchConfig(@Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
                          @Qualifier("gasStationJobRepository") JobRepository jobRepository,
                          GasStationRepository repository,
                          DateUtil dateUtil
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.repository = repository;
        this.dateUtil = dateUtil;
        this.READER_PATH = "src/main/resources/csv/" + dateUtil.getTodayDateString() + "/" + dateUtil.getTodayDateString() + "-";
    }

    @Bean(name = "lpgReader")
    @StepScope
    public FlatFileItemReader<GasStation> reader() {
        FlatFileItemReader<GasStation> itemReader = new FlatFileItemReader<>();
        String path = READER_PATH + "basic-info-lpg.csv";
        itemReader.setResource(new FileSystemResource(path)); // api 나 파일로부터 작업을 처리하도록 할 수 있음
        itemReader.setName("csvReader"); // itemReader 이름 설정
        itemReader.setEncoding("UTF-8");
        itemReader.setLinesToSkip(1); // 건너뛸 줄 설정
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean(name = "lpgProcess")
    public GasStationProcess processor() {
        return new GasStationProcess();
    }


    private LineMapper<GasStation> lineMapper() {
        DefaultLineMapper<GasStation> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(","); // 데이터 쉼표로 구분
        lineTokenizer.setStrict(false);
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4); // csv 에서 특정 열을 선택
        lineTokenizer.setNames("id", "area", "osName", "pollDivName", "newAddress"); // 요소의 열을 구분
//        고유번호,지역,상호,상표,주소,전화번호
        BeanWrapperFieldSetMapper<GasStation> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(GasStation.class); // 파일을 객체로 변환할 수 있도록 도와주는 객체

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean(name = "lpgWriter")
    public ItemWriter<GasStation> writer() {
        RepositoryItemWriter<GasStation> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean(name = "lpgImportStep")
    public Step importStep() {
        return new StepBuilder("lpgCsvImport", jobRepository)
                .<GasStation, GasStation>chunk(1000, platformTransactionManager) // 한번에 처리하려는 레코드 라인 수
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
    @Bean(name = "lpgJob")
    public Job runJob() {
        return new JobBuilder("importLpg", jobRepository)
                .start(importStep()) // .next 를 사용하여 다음 작업 수행할 수도 있음
                .build();
    }
}
