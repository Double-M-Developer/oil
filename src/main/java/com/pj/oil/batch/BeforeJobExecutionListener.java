package com.pj.oil.batch;

import com.pj.oil.gasStation.repository.maria.*;
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
    private final PriceOilRepository priceOilRepository;
    private final PriceLpgRepository priceLpgRepository;
    private final DateUtil dateUtil;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        switch (jobName) {
            case "importPriceLpg":
                priceLpgRepository.deleteAll();
                LOGGER.info("Deleted all data from PriceLpgRepository before starting the job: {}", jobName);
                break;
            case "importPriceOil":
                priceOilRepository.deleteAll();
                LOGGER.info("Deleted all data from PriceOilRepository before starting the job: {}", jobName);
                break;
            case "importLowTop20Price":
                lowTop20PriceRepository.deleteAll();
                LOGGER.info("Deleted all data from LowTop20PriceRepository before starting the job: {}", jobName);
                break;
            case "importAverageAllPrice":
                averageAllPriceRepository.deleteByTradeDate(dateUtil.getYesterdayDateString());
                LOGGER.info("Deleted all data from AverageAllPriceRepository before starting the job: {}", jobName);
                break;
            case "importAverageRecentPrice":
                averageRecentPriceRepository.deleteByDate(dateUtil.getYesterdayDateString());
                LOGGER.info("Deleted all data from AverageRecentPriceRepository before starting the job: {}", jobName);
                break;
            case "importAreaAverageRecentPrice":
                areaAverageRecentPriceRepository.deleteByBaseDate(dateUtil.getYesterdayDateString());
                LOGGER.info("Deleted all data from AreaAverageRecentPriceRepository before starting the job: {}", jobName);
                break;
            default:
                LOGGER.warn("No specific pre-processing required for this job: {}", jobName);
                break;
        }
    }
}
