package com.pj.oil.util;

import com.pj.oil.config.PropertyConfiguration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

@Component
public class CrawlerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerUtil.class);

    private final PropertyConfiguration config;
    private final DateUtil dateUtil;

    private static String downloadPathWithDate;

    public CrawlerUtil(
            PropertyConfiguration config,
            DateUtil dateUtil
    ) {
        this.config = config;
        this.dateUtil = dateUtil;
        System.setProperty("file.encoding", "UTF-8");
    }

    public boolean downloadCSVFromWeb() {
        LOGGER.info("[downloadCSVFromWeb]");
        WebDriver driver = initializeWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
        boolean downloadOilData;
        boolean downloadLpgData;
        try {
            navigateToPage(driver, config.getCsvDownloadUrl());
            LOGGER.info("[downloadCSVFromWeb] 주유소 데이터 다운로드");
            downloadOilData = downloadData(driver, wait, false);// 주유소 데이터 다운로드
            switchToChargeStation(driver);
            downloadLpgData = downloadData(driver, wait, true);// 충전소 데이터 다운로드
            LOGGER.info("[downloadCSVFromWeb] 충전소 데이터 다운로드");
        } finally {
            LOGGER.info("[downloadCSVFromWeb] driver quit");
            driver.quit();
        }
        return downloadOilData&&downloadLpgData;
    }

    private WebDriver initializeWebDriver() {
        LOGGER.info("[initializeWebDriver]");
        LOGGER.info("driver path:{} chrome path: {}", config.getWebDriverPath(), config.getChromePath());
        LOGGER.info("user-agent : {}", config.getUserAgent());

        System.setProperty("webdriver.chrome.driver", config.getWebDriverPath());
        ChromeOptions options = new ChromeOptions();
        options.setBinary(config.getChromePath());
        options.addArguments(config.getUserAgent());
        options.addArguments("--headless"); //브라우저 안띄움
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-popup-blocking"); //팝업안띄움
        options.addArguments("--disable-gpu"); //gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
        options.addArguments("--disable-dev-shm-usage"); //Linux /dev/shm 메모리 공유 비활성화

        downloadPathWithDate = config.getDownloadFilepath() + dateUtil.getTodayDateString();
        LOGGER.info("downloadPathWithDate: {}", downloadPathWithDate);
        Path downloadDir = Paths.get(downloadPathWithDate);
        if (!Files.exists(downloadDir)) {
            try {
                Files.createDirectories(downloadDir);
                LOGGER.info("디렉토리 생성 성공: {}", downloadDir);
            } catch (IOException e) {
                LOGGER.error("디렉토리 생성 중 에러 발생: {}", downloadDir, e);
                throw new RuntimeException("디렉토리 생성 실패", e);
            }
        }

        configureDownloadOptions(options, downloadPathWithDate);
        return new ChromeDriver(options);
    }

    private boolean downloadData(WebDriver driver, WebDriverWait wait, boolean isChargeStation) {
        LOGGER.info("[downloadData]");
        boolean downloadBasicCSV = downloadCSV(driver, wait, config.getTypeBasicInfo(), isChargeStation);
        boolean downloadPriceCSV = downloadCSV(driver, wait, config.getTypeCurrentPrice(), isChargeStation);
        return downloadBasicCSV && downloadPriceCSV;
    }

    private void configureDownloadOptions(ChromeOptions options, String downloadFilepath) {
        LOGGER.info("[configureDownloadOptions] downloadFilepath: {}", downloadFilepath);
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", "false");
        prefs.put("safebrowsing.enabled", "true");
        options.setExperimentalOption("prefs", prefs);
    }

    private boolean downloadCSV(WebDriver driver, WebDriverWait wait, int downloadType, boolean isChargeStation) {
        LOGGER.info("[downloadCSV] downloadType: {}", downloadType);
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMinutes(5)) // 총 대기 시간을 넉넉하게 설정
                .pollingEvery(Duration.ofSeconds(1)) // 폴링 간격 설정
                .ignoring(WebDriverException.class);

        int attempt = 0;
        boolean downloadCompleted = false;

        while (attempt < 3 && !downloadCompleted) { // 최대 3번 시도
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("fn_Download(" + downloadType + ");");
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                LOGGER.info("alert: {}", alert);
                alert.accept();

                String expectedFileName = getExpectedFileName(downloadType, isChargeStation);

                downloadCompleted = fluentWait.until((WebDriver webDriver) -> {
                    LOGGER.info("[downloadCSV] check downloadCompleted, downloadPathWithDate: {}, expectedFileName: {}", downloadPathWithDate, expectedFileName);
                    Path downloadFilePath = Paths.get(downloadPathWithDate, expectedFileName);

                    return Files.exists(downloadFilePath) && !downloadFilePath.toString().endsWith(".crdownload");
                });

                if (downloadCompleted) {
                    LOGGER.info("다운로드 완료: {}", expectedFileName);
                } else {
                    LOGGER.warn("다운로드 실패, 재시도 중...: {}", expectedFileName);
                    attempt++;
                }
            } catch (Exception e) {
                LOGGER.error("다운로드 중 예외 발생: {}", e.getMessage());
                attempt++;
                if (attempt >= 3) {
                    LOGGER.error("다운로드 최대 재시도 횟수 초과: {}", e.getMessage());
                    break;
                }
            }
        }
        return downloadCompleted;
    }

    private String getExpectedFileName(int downloadType, boolean isChargeStation) {
        LOGGER.info("[getExpectedFileName]");
        String fileName;
        if (downloadType == config.getTypeBasicInfo()) {
            if (isChargeStation) {
                fileName = config.getBasicInfoLpg();
            } else {
                fileName = config.getBasicInfoOil();
            }
        } else { // downloadType == TYPE_CURRENT_PRICE
            if (isChargeStation) {
                fileName = config.getCurrentPriceLpg();
            } else {
                fileName = config.getCurrentPriceOil();
            }
        }
        LOGGER.info("[getExpectedFileName] filename: {}", fileName);
        return fileName;
    }

    private void navigateToPage(WebDriver driver, String url) {
        LOGGER.info("[navigateToPage] url: {}", url);
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("tbl_type20")));
    }

    private void switchToChargeStation(WebDriver driver) {
        LOGGER.info("[switchToChargeStation]");
        // 자동차 충전소 라디오 버튼 선택
        driver.findElement(By.id("rdo1_1")).click();
        driver.findElement(By.id("rdo2_1")).click();
    }
}