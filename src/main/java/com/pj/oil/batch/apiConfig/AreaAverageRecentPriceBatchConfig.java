package com.pj.oil.batch.apiConfig;

import com.pj.oil.batch.process.AreaAverageRecentPriceProcess;
import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
import com.pj.oil.gasStation.repository.jpa.AreaAverageRecentPriceRepository;
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

import java.util.List;


@Configuration
@EnableBatchProcessing(
        dataSourceRef = "gasStationDataSource",
        transactionManagerRef = "gasStationTransactionManager")
public class AreaAverageRecentPriceBatchConfig  {

    private static String[] areas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "14", "15", "16", "17", "18", "19"};
    private static String date = DateUtil.getYesterdayDateString();
    private final PlatformTransactionManager platformTransactionManager;
    private final AreaAverageRecentPriceRepository repository;
    private final JobRepository jobRepository;
    public AreaAverageRecentPriceBatchConfig(
            @Qualifier("gasStationJobRepository") JobRepository jobRepository,
            @Qualifier("gasStationTransactionManager") PlatformTransactionManager platformTransactionManager,
            AreaAverageRecentPriceRepository repository
    ) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
        this.repository = repository;
    }

    @Bean(name = "areaAverageRecentPriceReader")
    public ItemReader<AreaAverageRecentPrice> reader() {
        return new ItemReader<AreaAverageRecentPrice>() {
            private int currentAreaIndex = 0;
            private List<AreaAverageRecentPrice> currentDataList = null;
            private int currentDataIndex = 0;

            @Override
            public AreaAverageRecentPrice read() {
//                // 현재 데이터 리스트를 모두 읽었으면 다음 지역의 데이터를 가져옴
//                if (currentDataList == null || currentDataIndex >= currentDataList.size()) {
//                    if (currentAreaIndex < areas.length) {
//                        String areaCode = areas[currentAreaIndex++];
//                        currentDataList = getGasStationBaseService().getAreaAvgRecentNDateAllProdPrice(areaCode, date); // 고정된 날짜 사용
//                        currentDataIndex = 0; // 리스트의 처음부터 다시 시작
//                    } else {
//                        return null; // 모든 지역 데이터를 처리했으므로 null 반환
//                    }
//                }
//
//                // 현재 지역의 다음 데이터 반환
//                AreaAverageRecentPrice nextData = currentDataList.get(currentDataIndex);
//                currentDataIndex++;
//                return nextData;
                return null;
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

