//package com.pj.oil.batch;
//
//import com.pj.oil.gasStation.entity.GasStation;
//import com.pj.oil.gasStation.repository.GasStationRepository;
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//
//@Configuration
//@EnableBatchProcessing(
//        dataSourceRef = "gasStationDataSource",
//        transactionManagerRef = "gasStationTransactionManager")
//public class BatchConfig {
//
//    private final PlatformTransactionManager transactionManager;
//    private final GasStationRepository gasStationRepository;
//    private final JobRepository jobRepository;
//
//    public BatchConfig(@Qualifier("gasStationTransactionManager") PlatformTransactionManager transactionManager,
//                       JobRepository jobRepository,
//                       GasStationRepository gasStationRepository) {
//        this.transactionManager = transactionManager;
//        this.jobRepository = jobRepository;
//        this.gasStationRepository = gasStationRepository;
//    }
//
//    @Bean
//    public FlatFileItemReader<GasStation> reader() {
//        FlatFileItemReader<GasStation> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new FileSystemResource("src/main/resources/oil.csv"));
//        itemReader.setName("csvReader");
//        itemReader.setLinesToSkip(1);
//        itemReader.setLineMapper(lineMapper());
//        return itemReader;
//    }
//
//    @Bean
//    public GasStationProcessor processor() {
//        return new GasStationProcessor();
//    }
//
//    @Bean
//    public RepositoryItemWriter<GasStation> writer() {
//        RepositoryItemWriter<GasStation> writer = new RepositoryItemWriter<>();
//        writer.setRepository(gasStationRepository);
//        writer.setMethodName("save");
//        return writer;
//    }
//
//    @Bean
//    public Step importStep() {
//        return new StepBuilder("csvImport", jobRepository)
//                .<GasStation, GasStation>chunk(1000, transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        asyncTaskExecutor.setConcurrencyLimit(10);
//        return asyncTaskExecutor;
//    }
//
//    @Bean
//    public Job runJob() {
//        return new JobBuilder("importGasStation", jobRepository)
//                .start(importStep())
//                .build();
//    }
//
//    private LineMapper<GasStation> lineMapper() {
//        DefaultLineMapper<GasStation> lineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames(new String[]{"id", "area", "brand", "addr", "brand2", "self", "oil1", "oil2", "oli3"});
//
//        BeanWrapperFieldSetMapper<GasStation> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(GasStation.class);
//
//        lineMapper.setLineTokenizer(lineTokenizer);
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return lineMapper;
//    }
//
//}
