package com.pj.oil.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    // 날짜 형식을 정의하는 상수
    private static final String DATE_FORMAT = "yyyyMMdd";

    public String formatDateString(String dateString) {
        DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
        DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, originalFormat);
        return date.format(targetFormat);
    }

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

    /**
     * 어제 날짜부터 과거로 8일전의 날짜를 yyyyMMdd 형식의 문자열 배열로 반환합니다.
     * @return 8일전의 날짜를 담은 문자열 배열
     */
    public String getSevenDaysBeforeDateString() {
        LocalDate sevenDaysBefore = LocalDate.now().minusDays(7);
        return sevenDaysBefore.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * 어제 날짜부터 과거로 7일간의 날짜를 yyyyMMdd 형식의 문자열 배열로 반환합니다.
     * @return 어제 날짜부터 과거로 7일간의 날짜를 담은 문자열 배열
     */
    public String[] getLastSevenDays() {
        String[] lastSevenDays = new String[7];
        LocalDate startDay = LocalDate.now().minusDays(1);

        for (int i = 0; i < 7; i++) {
            // startDay에서 i일을 뺀 날짜를 계산
            LocalDate date = startDay.minusDays(i);
            // 계산된 날짜를 지정된 형식으로 변환하여 배열에 저장
            lastSevenDays[i] = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        }

        return lastSevenDays;
    }
}
