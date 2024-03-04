package com.pj.oil.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PropertyConfiguration propertyConfiguration;
    private MeterRegistry meterRegistry;

    public WebConfig(PropertyConfiguration propertyConfiguration, MeterRegistry meterRegistry) {
        this.propertyConfiguration = propertyConfiguration;
        this.meterRegistry = meterRegistry;
    }
    @Bean
    public RestClient restClient() {
        ClientHttpRequestInterceptor metricsInterceptor = (request, body, execution) -> {
            long start = System.currentTimeMillis();
            try {
                var response = execution.execute(request, body);
                recordMetrics(request.getURI().toString(), response.getStatusCode().value(), System.currentTimeMillis() - start);
                return response;
            } catch (IOException e) {
                recordMetrics(request.getURI().toString(), 500, System.currentTimeMillis() - start);
                throw e;
            }
        };

        return RestClient.builder()
                .requestInterceptors(interceptors -> interceptors.add(metricsInterceptor))
                .build();
    }

    private void recordMetrics(String uri, int statusCode, long requestDuration) {
        Timer.builder("oil.api.requests")
                .tags("uri", uri, "status", String.valueOf(statusCode))
                .register(meterRegistry)
                .record(requestDuration, TimeUnit.MILLISECONDS);
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