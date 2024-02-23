# Java 17 JDK를 사용하는 베이스 이미지
FROM openjdk:17-jdk-slim as build

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    locales \
    && rm -rf /var/lib/apt/lists/* \
    && sed -i '/ko_KR.UTF-8/s/^# //g' /etc/locale.gen \
    && locale-gen \

# 로케일 설정 추가
ENV LANG ko_KR.UTF-8
ENV LANGUAGE ko_KR:kr
ENV LC_ALL ko_KR.UTF-8

# Chrome headless shell과 ChromeDriver 다운로드 및 설치
RUN wget -q --continue -P /tmp "https://storage.googleapis.com/chrome-for-testing-public/121.0.6167.184/linux64/chrome-headless-shell-linux64.zip" \
    && unzip /tmp/chrome-headless-shell-linux64.zip -d /usr/local/bin \
    && rm /tmp/chrome-headless-shell-linux64.zip \
    && chmod +x /usr/local/bin/chrome-headless-shell-linux64 \
    && wget -q --continue -P /tmp "https://storage.googleapis.com/chrome-for-testing-public/121.0.6167.184/linux64/chromedriver-linux64.zip" \
    && unzip /tmp/chromedriver-linux64.zip -d /usr/local/bin \
    && rm /tmp/chromedriver-linux64.zip \
    && chmod +x /usr/local/bin/chromedriver-linux64

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼와 프로젝트 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# gradlew 파일의 줄바꿈 포맷을 LF로 변경 및 실행 권한 부여
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew

# 애플리케이션 빌드 (단위 테스트 제외)
RUN ./gradlew build -x test

# 최종 실행 이미지
FROM openjdk:17-jdk-slim
WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget \
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    libx11-6 \
    libx11-xcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxext6 \
    libxi6 \
    libxtst6 \
    libgdk-pixbuf2.0-0 \
    libgtk-3-0 \
    libpango-1.0-0 \
    libcairo2 \
    libatspi2.0-0 \
    libatk1.0-0 \
    libasound2 \
    libdbus-1-3 \
    libxss1 \
    libxrandr2 \
    libgbm1 \
    locales \
    --no-install-recommends \
    && rm -rf /var/lib/apt/lists/* \
    && sed -i '/ko_KR.UTF-8/s/^# //g' /etc/locale.gen \
    && locale-gen

# 로케일 설정 추가
ENV LANG ko_KR.UTF-8
ENV LANGUAGE ko_KR:kr
ENV LC_ALL ko_KR.UTF-8

COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /usr/local/bin/chrome-headless-shell-linux64 /usr/local/bin/chrome
COPY --from=build /usr/local/bin/chromedriver-linux64 /usr/local/bin/chromedriver

ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","app.jar"]
