package com.pj.oil.gasStation;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProductRegistry {

    private final Map<String, String> productMap = new HashMap<>();
    private String[] productCodes;

    @PostConstruct
    public void init() {
        productMap.put("B034", "고급휘발유");
        productMap.put("B027", "휘발유");
        productMap.put("D047", "경유");
        productMap.put("K015", "LPG");
        initializeProductCodes();
    }

    private void initializeProductCodes() {
        productCodes = productMap.keySet().toArray(new String[0]);
    }

    public Map<String, String> getProductMap() {
        return Collections.unmodifiableMap(productMap);
    }

    public String[] getProductCodes() {
        return productCodes.clone(); // 배열의 불변성을 유지하기 위해 복사본 반환
    }

    public String getProductName(String code) {
        return productMap.getOrDefault(code, "기타");
    }
}
