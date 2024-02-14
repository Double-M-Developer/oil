package com.pj.oil.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    // 날짜 형식을 정의하는 상수
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 오늘 날짜를 yyyyMMdd 형식의 문자열로 반환합니다.
     * @return 오늘 날짜를 나타내는 문자열
     */
    public String getTodayDateString() {
        // 현재 날짜에서 하루를 빼서 어제 날짜를 구함
        LocalDate today = LocalDate.now();

        // 날짜를 지정된 형식의 문자열로 변환
        return today.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * 어제 날짜를 yyyyMMdd 형식의 문자열로 반환합니다.
     * @return 어제 날짜를 나타내는 문자열
     */
    public String getYesterdayDateString() {
        // 현재 날짜에서 하루를 빼서 어제 날짜를 구함
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 날짜를 지정된 형식의 문자열로 변환
        return yesterday.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
