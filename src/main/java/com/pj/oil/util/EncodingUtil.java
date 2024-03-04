package com.pj.oil.util;

import com.pj.oil.config.PropertyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class EncodingUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingUtil.class);
    private static final int CHUNK_SIZE = 1000; // 청크 크기 설정

    private final PropertyConfiguration config;

    private final String inputPathWithDate;
    private final String outputPathWithDate;

    public EncodingUtil(PropertyConfiguration config) {
        this.config = config;
        this.inputPathWithDate = config.getDownloadFilepath() + DateUtil.getTodayDateString() + "/";
        this.outputPathWithDate = config.getOutFilepath() + DateUtil.getTodayDateString() + "/" + DateUtil.getTodayDateString() + "-";
    }

    public boolean convertFileEncoding(String inputFileName, String outputFileName) {
        String input = inputPathWithDate + inputFileName;
        String output = outputPathWithDate + outputFileName;
        LOGGER.info("[convertFileEncoding] input: {}, output: {}", input, output);
        Path inputPath = Paths.get(input);
        Path outputPath = Paths.get(output);

        // 디렉토리 확인 및 생성
        Path parentDir = outputPath.getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                LOGGER.error("디렉토리 생성 중 에러 발생: {}", parentDir, e);
                return false;
            }
        }

        int attempt = 0;
        boolean encodingCompleted = false;
        while(attempt < 3 && !encodingCompleted) {

            try (BufferedReader reader = Files.newBufferedReader(inputPath, Charset.forName("Cp949"));
                 BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {

                List<String> buffer = new ArrayList<>(CHUNK_SIZE);
                reader.lines().forEach(line -> {
                    buffer.add(line);
                    if (buffer.size() == CHUNK_SIZE) {
                        flushBuffer(buffer, writer);
                    }
                });

                // 남은 데이터 처리
                flushBuffer(buffer, writer);
                encodingCompleted = true;
            } catch (IOException e) {
                LOGGER.error("파일 인코딩 변환 중 에러 발생: {}", e.getMessage(), e);
                attempt++;
                if(attempt >= 3) {
                    LOGGER.error("파일 인코딩 변환 최대 재시도 횟수 초과: {}", e.getMessage(), e);
                    break;
                }
            }
        }
        return encodingCompleted;
    }

    private void flushBuffer(List<String> buffer, BufferedWriter writer) {
        try {
            for (String line : buffer) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("버퍼 데이터 쓰기 중 에러 발생: {}", e.getMessage(), e);
        } finally {
            buffer.clear(); // 버퍼 초기화
        }
    }
}