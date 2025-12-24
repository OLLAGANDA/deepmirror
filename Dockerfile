# 1. 빌드 단계 (Gradle 환경)
FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .

# 실행 권한 부여
RUN chmod +x ./gradlew

# ★★★ 이 줄이 빠져서 에러가 난 겁니다! (빌드 실행) ★★★
RUN ./gradlew build -x test

# 2. 실행 단계 (가벼운 JDK 환경)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]