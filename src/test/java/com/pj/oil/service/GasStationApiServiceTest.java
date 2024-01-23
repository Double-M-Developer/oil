package com.pj.oil.service;

import com.pj.oil.config.PropertyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GasStationApiServiceTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Mock
    private PropertyConfiguration mockProperties;

    @Mock
    private GasStationHttpInterface mockService;


    @InjectMocks
    private GasStationApiService gasStationApiService;

    private final String mockApiKey = "test-api-key";
    private final String mockBaseUrl = "base";

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        // GasStationApiService 내부에서 사용되는 mock 객체들이 올바르게 설정되었는지 확인합니다.
        assertNotNull(gasStationApiService, "gasStationApiService should not be null");
        assertNotNull(mockProperties, "mockProperties should not be null");
        assertNotNull(mockService, "mockService should not be null");
        when(mockProperties.getApiKey()).thenReturn(mockApiKey);
        when(mockProperties.getBaseUrl()).thenReturn(mockBaseUrl);
    }

    @Test
    public void testGetApiKey() {
        String apiKey = mockProperties.getApiKey();
        System.out.println("Mocked API Key: " + apiKey);
        String baseUrl = mockProperties.getBaseUrl();
        System.out.println("Mocked base Url: " + baseUrl);
        assertEquals(mockApiKey, apiKey, "The API key should match the mocked key");
        assertEquals(mockBaseUrl, baseUrl, "The API key should match the mocked key");
    }

}