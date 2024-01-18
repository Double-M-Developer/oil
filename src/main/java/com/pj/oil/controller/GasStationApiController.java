package com.pj.oil.controller;

import com.pj.oil.service.GasStationApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class GasStationApiController {

    private final GasStationApiService gasStationApiService;

    @GetMapping("/v1/api/test/area")
    public Mono<String>fetch() throws UnsupportedEncodingException {
        return gasStationApiService.fetch()
                .doOnSuccess(result -> {
                    // 비동기 작업이 완료된 후 실행되는 코드
                    System.out.println("Received result: " + result);
                    // 여기에서 결과를 원하는 방식으로 처리합니다.
                });
    }
}
