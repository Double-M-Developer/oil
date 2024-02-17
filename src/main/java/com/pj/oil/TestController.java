package com.pj.oil;

import com.pj.oil.config.PropertyConfiguration;
import com.pj.oil.util.CrawlerUtil;
import com.pj.oil.util.EncodingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
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
    private final Job areaAverageRecentPriceJob;
    private final Job averageRecentPriceJob;
    private final Job averageAllPriceJob;
    private final Job lowTop20PriceJob;


    public TestController(
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
            @Qualifier("lowTop20PriceJob") Job lowTop20PriceJob
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
    }

    @GetMapping("/test/download-csv-db")
    public void downloadCsvDb() {
        LOGGER.info("downloadCsvDb 실행 중...");
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
                LOGGER.info("downloadCsvDb 완료");
            } else {
                LOGGER.warn("downloadCsvDb 실패");
            }
        }
    }

    @GetMapping("/test/oilJob")
        public String testOilJob() {
            LOGGER.info("testOilJob 실행 중...");
            try {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters();
                JobExecution jobExecution = jobLauncher.run(oilJob, jobParameters);
                return "Job ID: " + jobExecution.getId() + " 상태: " + jobExecution.getStatus();
            } catch (Exception e) {
                e.printStackTrace();
                return "작업 실행 중 오류가 발생했습니다.";
            }
        }
    @GetMapping("/test/priceOilJob")
    public String testPriceOilJob() {
        LOGGER.info("testPriceOilJob 실행 중...");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(priceOilJob, jobParameters);
            return "Job ID: " + jobExecution.getId() + " 상태: " + jobExecution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "작업 실행 중 오류가 발생했습니다.";
        }
    }
    @GetMapping("/test/lpgJob")
    public String testLpgJob() {
        LOGGER.info("testLpgJob 실행 중...");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(lpgJob, jobParameters);
            return "Job ID: " + jobExecution.getId() + " 상태: " + jobExecution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "작업 실행 중 오류가 발생했습니다.";
        }
    }
    @GetMapping("/test/priceLpgJob")
    public String testPriceLpgJob() {
        LOGGER.info("testPriceLpgJob 실행 중...");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(priceLpgJob, jobParameters);
            return "Job ID: " + jobExecution.getId() + " 상태: " + jobExecution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "작업 실행 중 오류가 발생했습니다.";
        }
    }
    @GetMapping("/test/lowTop20PriceJob")
    public String testLowTop20PriceJob() {
        LOGGER.info("testLowTop20PriceJob 실행 중...");
        try {
            // JobParameters를 생성하여 배치 작업 실행에 필요한 파라미터를 설정할 수 있습니다.
            // 예제에서는 현재 시간을 기준으로 고유한 JobParameters를 생성합니다.
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // JobLauncher를 사용하여 배치 작업을 실행합니다.
            JobExecution jobExecution = jobLauncher.run(lowTop20PriceJob, jobParameters);

            // 실행 결과를 반환합니다.
            return String.format("Job ID: %d, 실행 상태: %s", jobExecution.getId(), jobExecution.getStatus());
        } catch (Exception e) {
            return "배치 작업 실행 중 오류가 발생했습니다: " + e.getMessage();
        }
    }

    @GetMapping("/test/averageAllPriceJob")
    public String testAverageAllPriceJob() {
        LOGGER.info("testAverageAllPriceJob 실행 중...");
        try {
            // JobParameters를 생성하여 배치 작업 실행에 필요한 파라미터를 설정할 수 있습니다.
            // 예제에서는 현재 시간을 기준으로 고유한 JobParameters를 생성합니다.
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // JobLauncher를 사용하여 배치 작업을 실행합니다.
            JobExecution jobExecution = jobLauncher.run(averageAllPriceJob, jobParameters);

            // 실행 결과를 반환합니다.
            return String.format("Job ID: %d, 실행 상태: %s", jobExecution.getId(), jobExecution.getStatus());
        } catch (Exception e) {
            return "배치 작업 실행 중 오류가 발생했습니다: " + e.getMessage();
        }
    }

    @GetMapping("/test/averageRecentPriceJob")
    public String testAverageRecentPriceJob() {
        LOGGER.info("testAverageRecentPriceJob 실행 중...");
        try {
            // JobParameters를 생성하여 배치 작업 실행에 필요한 파라미터를 설정할 수 있습니다.
            // 예제에서는 현재 시간을 기준으로 고유한 JobParameters를 생성합니다.
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // JobLauncher를 사용하여 배치 작업을 실행합니다.
            JobExecution jobExecution = jobLauncher.run(averageRecentPriceJob, jobParameters);

            // 실행 결과를 반환합니다.
            return String.format("Job ID: %d, 실행 상태: %s", jobExecution.getId(), jobExecution.getStatus());
        } catch (Exception e) {
            return "배치 작업 실행 중 오류가 발생했습니다: " + e.getMessage();
        }
    }

    @GetMapping("/test/areaAverageRecentPriceJob")
    public String testAreaAverageRecentPriceJob() {
        LOGGER.info("testAreaAverageRecentPriceJob 실행 중...");
        try {
            // JobParameters를 생성하여 배치 작업 실행에 필요한 파라미터를 설정할 수 있습니다.
            // 예제에서는 현재 시간을 기준으로 고유한 JobParameters를 생성합니다.
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // JobLauncher를 사용하여 배치 작업을 실행합니다.
            JobExecution jobExecution = jobLauncher.run(areaAverageRecentPriceJob, jobParameters);

            // 실행 결과를 반환합니다.
            return String.format("Job ID: %d, 실행 상태: %s", jobExecution.getId(), jobExecution.getStatus());
        } catch (Exception e) {
            return "배치 작업 실행 중 오류가 발생했습니다: " + e.getMessage();
        }
    }
}
