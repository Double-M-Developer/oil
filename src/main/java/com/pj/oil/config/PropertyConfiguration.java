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
}
