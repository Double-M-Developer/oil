package com.pj.oil.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PropertyConfiguration propertyConfiguration;

    public WebConfig(PropertyConfiguration propertyConfiguration) {
        this.propertyConfiguration = propertyConfiguration;
    }

    @Bean
    public GasStationHttpInterface gasStationHttpInterface() {
        LOGGER.info("[gasStationHttpInterface]");
        RestClient client = RestClient.builder()
                .baseUrl(propertyConfiguration.getBaseUrl())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    HttpStatusCode statusCode = response.getStatusCode();
                    LOGGER.info("[gasStationHttpInterface] status code: {}", statusCode);
                })
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(client);
        return HttpServiceProxyFactory.builderFor(adapter).build().createClient(GasStationHttpInterface.class);
    }

}
