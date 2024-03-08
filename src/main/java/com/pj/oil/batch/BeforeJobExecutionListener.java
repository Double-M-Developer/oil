package com.pj.oil.batch;

import com.pj.oil.gasStation.repository.*;
import com.pj.oil.cache.gasStation.repository.AreaAverageRecentPriceRedisRepository;
import com.pj.oil.cache.gasStation.repository.AverageAllPriceRedisRepository;
import com.pj.oil.cache.gasStation.repository.AverageRecentPriceRedisRepository;
import com.pj.oil.cache.gasStation.repository.LowTop20PriceRedisRepository;
import com.pj.oil.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional("gasStationTransactionManager")
@RequiredArgsConstructor
public class BeforeJobExecutionListener implements JobExecutionListener {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final LowTop20PriceRepository lowTop20PriceRepository;
    private final AreaAverageRecentPriceRepository areaAverageRecentPriceRepository;
    private final AverageRecentPriceRepository averageRecentPriceRepository;
    private final AverageAllPriceRepository averageAllPriceRepository;

    private final LowTop20PriceRedisRepository lowTop20PriceRedisRepository;
    private final AreaAverageRecentPriceRedisRepository areaAverageRecentPriceRedisRepository;
    private final AverageRecentPriceRedisRepository averageRecentPriceRedisRepository;
    private final AverageAllPriceRedisRepository averageAllPriceRedisRepository;

    private final PriceOilRepository priceOilRepository;
    private final PriceLpgRepository priceLpgRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        switch (jobName) {
            case "importPriceLpg":
                priceLpgRepository.deleteAllData();
                LOGGER.info("Deleted all data from PriceLpgRepository before starting the job: {}", jobName);
                break;
            case "importPriceOil":
                priceOilRepository.deleteAllData();
                LOGGER.info("Deleted all data from PriceOilRepository before starting the job: {}", jobName);
                break;
            case "importLowTop20Price":
                lowTop20PriceRepository.deleteAllData();
                LOGGER.info("Deleted all data from LowTop20PriceRepository before starting the job: {}", jobName);
                break;
            case "importAverageAllPrice":
                averageAllPriceRepository.deleteByTradeDate(DateUtil.getYesterdayDateString());
                LOGGER.info("Deleted all data from AverageAllPriceRepository before starting the job: {}, date: {}", jobName, DateUtil.getYesterdayDateString());
                break;
            case "importAverageRecentPrice":
                averageRecentPriceRepository.deleteByDate(DateUtil.getYesterdayDateString());
                LOGGER.info("Deleted all data from AverageRecentPriceRepository before starting the job: {}, date: {}", jobName, DateUtil.getYesterdayDateString());
                break;
            case "importAreaAverageRecentPrice":
                areaAverageRecentPriceRepository.deleteByBaseDate(DateUtil.getYesterdayDateString());
                LOGGER.info("Deleted all data from AreaAverageRecentPriceRepository before starting the job: {}, date: {}", jobName, DateUtil.getYesterdayDateString());
                break;
            default:
                LOGGER.warn("No specific pre-processing required for this job: {}", jobName);
                break;
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        switch (jobName) {
            case "importLowTop20Price":
                lowTop20PriceRedisRepository.deleteAll();
                LOGGER.info("Deleted all data from LowTop20PriceRepository before starting the job: {}", jobName);
                break;
            case "importAverageAllPrice":
                averageAllPriceRedisRepository.deleteAll();
                LOGGER.info("Deleted all data from AverageAllPriceRepository before starting the job: {}", jobName);
                break;
            case "importAverageRecentPrice":
                averageRecentPriceRedisRepository.deleteAll();
                LOGGER.info("Deleted all data from AverageRecentPriceRepository before starting the job: {}", jobName);
                break;
            case "importAreaAverageRecentPrice":
                areaAverageRecentPriceRedisRepository.deleteAll();
                LOGGER.info("Deleted all data from AreaAverageRecentPriceRepository before starting the job: {}", jobName);
                break;
            default:
                LOGGER.warn("No specific post-processing required for this job: {}", jobName);
                break;
        }
    }
}
