package com.pj.oil.scheduler;

import com.pj.oil.config.PropertyConfiguration;
import com.pj.oil.util.CrawlerUtil;
import com.pj.oil.util.EncodingUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GasStationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GasStationScheduler.class);
    private final CrawlerUtil crawlerUtil;
    private final EncodingUtil encodingUtil;
    private final PropertyConfiguration config;
    private final JobLauncher jobLauncher;
    //
    private final Job oilJob;
    private final Job priceOilJob;
    private final Job lpgJob;
    private final Job priceLpgJob;
    //
    private final Job averageRecentPriceJob;
    private final Job areaAverageRecentPriceJob;
    private final Job averageAllPriceJob;
    private final Job lowTop20PriceJob;
    //
    private final MeterRegistry meterRegistry;

    public GasStationScheduler(
            CrawlerUtil crawlerUtil,
            EncodingUtil encodingUtil,
            PropertyConfiguration config,
            JobLauncher jobLauncher,
            //
            @Qualifier("oilJob") Job oilJob,
            @Qualifier("priceOilJob") Job priceOilJob,
            @Qualifier("lpgJob") Job lpgJob,
            @Qualifier("priceLpgJob") Job priceLpgJob,
            //
            @Qualifier("averageRecentPriceJob") Job averageRecentPriceJob,
            @Qualifier("areaAverageRecentPriceJob") Job areaAverageRecentPriceJob,
            @Qualifier("averageAllPriceJob") Job averageAllPriceJob,
            @Qualifier("lowTop20PriceJob") Job lowTop20PriceJob,
            //
            MeterRegistry meterRegistry
    ) {
        this.crawlerUtil = crawlerUtil;
        this.encodingUtil = encodingUtil;
        this.config = config;
        this.jobLauncher = jobLauncher;
        this.oilJob = oilJob;
        this.priceOilJob = priceOilJob;
        this.lpgJob = lpgJob;
        this.priceLpgJob = priceLpgJob;
        this.averageRecentPriceJob = averageRecentPriceJob;
        this.areaAverageRecentPriceJob = areaAverageRecentPriceJob;
        this.averageAllPriceJob = averageAllPriceJob;
        this.lowTop20PriceJob = lowTop20PriceJob;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void saveCsv() {
        LOGGER.info("스케줄링 작업 실행 중...");
        long start = System.currentTimeMillis();
        boolean downloadCSVCompleted = crawlerUtil.downloadCSVFromWeb();
        if (downloadCSVCompleted) {
            String[] files = {config.getBasicInfoLpg(), config.getBasicInfoOil(), config.getCurrentPriceLpg(), config.getCurrentPriceOil()};
            String[] names = {"basic-info-lpg.csv", "basic-info-oil.csv", "current-price-lpg.csv", "current-price-oil.csv"};
            boolean encodingCompleted = false;
            for (int i = 0; i < files.length; i++) {
                encodingCompleted = encodingUtil.convertFileEncoding(files[i], names[i]);
                LOGGER.info("Encoding completed for: {}", names[i]);
            }
            if (downloadCSVCompleted && encodingCompleted) {
                LOGGER.info("스케줄링 작업 완료");
                long duration = System.currentTimeMillis() - start;
                LOGGER.info("saveCsv 실행 시간: {}ms", duration);
                Timer.builder("scheduler.jobs.duration")
                        .tags("job", "saveCsv")
                        .register(meterRegistry)
                        .record(duration, TimeUnit.MILLISECONDS);
            } else {
                LOGGER.warn("스케줄링 작업 실패");
                Counter.builder("scheduler.jobs.failures")
                        .tags("job", "saveCsv")
                        .register(meterRegistry)
                        .increment();
            }
        }
    }
    private void jobWithMonitoring(Job job) {
        String jobName = job.getName();
        LOGGER.info("{} 실행 중...",jobName);
        long start = System.currentTimeMillis();
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            LOGGER.info("Job ID: {} 상태: {}",jobExecution.getId(), jobExecution.getStatus());
            long duration = System.currentTimeMillis() - start;
            LOGGER.info("{} 실행 시간: {}ms", jobName, duration);
            Timer.builder("scheduler.jobs.duration")
                    .tags("job", jobName)
                    .register(meterRegistry)
                    .record(duration, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Counter.builder("scheduler.jobs.failures")
                    .tags("job", jobName)
                    .register(meterRegistry)
                    .increment();
            LOGGER.error("작업 실행 중 오류가 발생했습니다", e);
        }
    }
    @Scheduled(cron = "0 5 1 * * ?")
    public void saveOil() {
        jobWithMonitoring(oilJob);
    }
    @Scheduled(cron = "0 10 1 * * ?")
    public void savePriceOil() {
        jobWithMonitoring(priceOilJob);
    }
    @Scheduled(cron = "0 15 1 * * ?")
    public void saveLpg() {
        jobWithMonitoring(lpgJob);
    }

    @Scheduled(cron = "0 20 1 * * ?")
    public void savePriceLpg() {
        jobWithMonitoring(priceLpgJob);
    }

    @Scheduled(cron = "0 25 1 * * ?")
    public void saveLowTop20Price() {
        jobWithMonitoring(lowTop20PriceJob);
    }

    @Scheduled(cron = "0 26 1 * * ?")
    public void saveAverageAllPrice() {
        jobWithMonitoring(averageAllPriceJob);
    }

    @Scheduled(cron = "0 27 1 * * ?")
    public void saveAverageRecentPrice() {
        jobWithMonitoring(averageRecentPriceJob);
    }
    @Scheduled(cron = "0 28 1 * * ?")
    public void saveAreaAverageRecentPrice() {
        jobWithMonitoring(areaAverageRecentPriceJob);
    }
}