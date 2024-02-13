package com.pj.oil.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PropertyConfiguration {

    @Value("${api.key}")
    private String apiKey;
    @Value("${api.baseurl}")
    private String baseUrl;
    @Value("${api.datatype}")
    private String dataType;

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpirationSecond;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpirationSecond;

    @Value("${selenium.download-filepath}")
    private String downloadFilepath;
    @Value("${selenium.web-driver-path}")
    private String webDriverPath;
    @Value("${selenium.chrome-path}")
    private String chromePath;
    @Value("${selenium.type-basic-info}")
    private int typeBasicInfo;
    @Value("${selenium.type-current-price}")
    private int typeCurrentPrice;
    @Value("${selenium.user-agent}")
    private String userAgent;
    @Value("${selenium.basic-info-lpg}")
    private String basicInfoLpg;
    @Value("${selenium.basic-info-oil}")
    private String basicInfoOil;
    @Value("${selenium.current-price-lpg}")
    private String currentPriceLpg;
    @Value("${selenium.current-price-oil}")
    private String currentPriceOil;
    @Value("${selenium.csv-download-url}")
    private String csvDownloadUrl;
    @Value("${csv-encoding.output-filepath}")
    private String outFilepath;

}
