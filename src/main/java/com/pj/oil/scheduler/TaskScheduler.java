package com.pj.oil.scheduler;

import com.pj.oil.config.PropertyConfiguration;
import com.pj.oil.util.CrawlerUtil;
import com.pj.oil.util.EncodingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskScheduler.class);
    private final CrawlerUtil crawlerUtil;
    private final EncodingUtil encodingUtil;
    private final PropertyConfiguration config;

    public TaskScheduler(CrawlerUtil crawlerUtil, EncodingUtil encodingUtil, PropertyConfiguration config) {
        this.crawlerUtil = crawlerUtil;
        this.encodingUtil = encodingUtil;
        this.config = config;
    }

    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void saveCsv() {
        LOGGER.info("스케줄링 작업 실행 중...");
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
            } else {
                LOGGER.warn("스케줄링 작업 실패");
            }
        }
    }
}