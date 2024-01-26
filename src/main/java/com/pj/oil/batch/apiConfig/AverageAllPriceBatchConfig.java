//package com.pj.oil.batch.apiConfig;
//
//import com.pj.oil.batch.GenericBatchConfig;
//import com.pj.oil.batch.process.AreaAverageRecentPriceProcess;
//import com.pj.oil.cache.CacheService;
//import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
//import com.pj.oil.gasStationApi.GasStationApiService;
//import com.pj.oil.gasStationApi.dto.AreaAverageRecentPriceDto;
//import com.pj.oil.util.DateUtil;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.item.*;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.List;
//
//
//@Configuration
//public abstract class AverageAllPriceBatchConfig extends GenericBatchConfig<AreaAverageRecentPriceDto, AreaAverageRecentPrice> {
//
//    private static String[] areas = {"01","02","03","04","05","06","07","08","09","10","11","14","15","16","17","18","19"};
//    private static String date = DateUtil.getTodayDateString();
//
//    public AverageAllPriceBatchConfig(
//            JobRepository jobRepository,
//            PlatformTransactionManager platformTransactionManager,
//            DataSource dataSource,
//            GasStationApiService gasStationApiService,
//            CacheService cacheService)
//    {
//        super(
//                jobRepository,
//                platformTransactionManager,
//                dataSource,
//                gasStationApiService,
//                cacheService
//        );
//    }
//
//    @Override
//    protected ItemReader<AreaAverageRecentPriceDto> getItemReader(GasStationApiService service) {
//        return new ItemReader<AreaAverageRecentPriceDto>() {
//            private int currentAreaIndex = 0;
//            private List<AreaAverageRecentPriceDto> currentDataList = null;
//            private int currentDataIndex = 0;
//
//            @Override
//            public AreaAverageRecentPriceDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                // 현재 데이터 리스트를 모두 읽었으면 다음 지역의 데이터를 가져옴
//                if (currentDataList == null || currentDataIndex >= currentDataList.size()) {
//                    if (currentAreaIndex < areas.length) {
//                        String areaCode = areas[currentAreaIndex++];
//                        currentDataList = service.getAreaAvgRecentNDateAllProdPrice(areaCode, date); // 고정된 날짜 사용
//                        currentDataIndex = 0; // 리스트의 처음부터 다시 시작
//                    } else {
//                        return null; // 모든 지역 데이터를 처리했으므로 null 반환
//                    }
//                }
//
//                // 현재 지역의 다음 데이터 반환
//                AreaAverageRecentPriceDto nextData = currentDataList.get(currentDataIndex);
//                currentDataIndex++;
//                return nextData;
//            }
//        };
//    }
//
//
//    @Override
//    protected ItemProcessor<AreaAverageRecentPriceDto, AreaAverageRecentPrice> getItemProcessor(CacheService cacheService) {
//        return new AreaAverageRecentPriceProcess(cacheService);
//    }
//
//    @Override
//    protected JdbcBatchItemWriter<AreaAverageRecentPrice> getJdbcBatchItemWriter() {
//        return new JdbcBatchItemWriter<>();
//    }
//
//    @Override
//    protected String getWriterSql() {
//        return "INSERT INTO area_average_recent_price (area_average_recent_price_id, base_date, area_code, product_code, average_price) "
//                + "VALUES (:id, :baseDate, :area.areaCode, :product.productCode, :averagePrice);";
//    }
//
//
//    @Override
//    protected String getJobName() {
//        return "areaAverageRecentPriceJob";
//    }
//
//    @Override
//    protected String getStepName() {
//        return "areaAverageRecentPriceStep";
//    }
//
//    @Override
//    protected int getChunkSize() {
//        return 100;
//    }
//}
