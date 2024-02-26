package com.pj.oil.gasStation;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class PollDivRegistry {

    private final Map<String, String> pollDivMap = new HashMap<>();
    private String[] pollDivCodes;

    @PostConstruct
    public void init() {
        pollDivMap.put("SKE", "SK에너지");
        pollDivMap.put("GSC", "GS칼텍스");
        pollDivMap.put("HDO", "현대오일뱅크");
        pollDivMap.put("SOL", "S-OIL");
        pollDivMap.put("RTE", "자영알뜰");
        pollDivMap.put("RTX", "고속도로알뜰");
        pollDivMap.put("NHO", "농협알뜰");
        pollDivMap.put("ETC", "자가상표");
        pollDivMap.put("E1G", "E1");
        pollDivMap.put("SKG", "SK가스");
        initializePollDivCodes();
    }

    private void initializePollDivCodes() {
        pollDivCodes = pollDivMap.keySet().toArray(new String[0]);
    }

    public Map<String, String> getPollDivMap() {
        return Collections.unmodifiableMap(pollDivMap);
    }

    public String[] getPollDivCodes() {
        return pollDivCodes.clone(); // 배열의 불변성을 유지하기 위해 복사본 반환
    }

    public String getPollDivName(String code) {
        return pollDivMap.getOrDefault(code, "기타");
    }
}
