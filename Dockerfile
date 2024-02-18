# Java 17 JDK를 사용하는 베이스 이미지
FROM openjdk:17-jdk-slim as build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼와 프로젝트 파일 복사
COPY gradlew .
COPY gradle gradle
# gradlew 파일의 줄바꿈 포맷을 LF로 변경
RUN sed -i 's/\r$//' ./gradlew
RUN chmod +x ./gradlew
COPY build.gradle .
COPY settings.gradle .
COPY src src

# 애플리케이션 빌드 (단위 테스트 제외)
RUN ./gradlew build -x test

# 최종 실행 이미지
FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
