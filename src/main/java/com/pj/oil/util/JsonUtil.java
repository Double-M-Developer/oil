package com.pj.oil.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    /**
     * JSON 문자열을 지정된 대상 클래스의 객체로 변환합니다.
     *
     * @param jsonString JSON 문자열
     * @param valueType  대상 클래스 유형
     */
    public static <T> T convertJsonStringToObject(String jsonString, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            // 예외 처리 및 로깅 추가
            throw new RuntimeException("Error converting JSON string to object", e);
        }
    }

    /**
     * Java 객체를 JSON 문자열로 변환합니다.
     *
     * @param object Java 객체
     * @return JSON 표현을 포함하는 문자열
     */
    public static String convertObjectToJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * JSON 문자열을 지정된 클래스의 객체 목록으로 변환합니다.
     *
     * @param jsonString JSON 문자열
     * @param clazz      목록의 각 요소의 클래스 유형
     */
    public static <T> List<T> convertJsonStringToList(String jsonString, Class<T> clazz) {
        try {
            CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(jsonString, listType);
        } catch (IOException e) {
            // 예외 처리 및 로깅 추가
            throw new RuntimeException("Error converting JSON string to list", e);
        }
    }
}