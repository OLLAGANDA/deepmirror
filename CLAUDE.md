# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) for the DeepMirror project.

## 1. 프로젝트 개요
DeepMirror는 성격 분석 및 미러링을 위한 Spring Boot 기반 웹 애플리케이션입니다.
사용자의 응답을 분석하여 성격 특성을 파악하고 피드백을 수집하는 시스템입니다.

- **Environment**: Ubuntu Server 24.04, Docker Compose
- **Tech Stack**:
  - Backend: Java 17, Spring Boot 3.4.0, Gradle (Groovy)
  - Database: PostgreSQL 15, JPA/Hibernate
  - AI: gemini-3-flash-preview
  - Frontend: Thymeleaf, HTML/CSS/JavaScript
  - API Documentation: SpringDoc OpenAPI (Swagger UI)

## 2. 코딩 컨벤션 (매우 중요 ★★★)
이 규칙은 모든 코드 생성 시 반드시 지켜야 합니다.
1. **NO LOMBOK**: Lombok 어노테이션(@Data, @Getter, @Builder 등)을 **절대 사용하지 마십시오.**
   - Getter, Setter, 생성자, Builder 패턴이 필요하면 **직접 코드로 작성**하십시오.
   - 이유: 코드의 명시성과 순수 Java 구조 유지를 위함.
2. **생성자 주입(Constructor Injection)**: `@Autowired` 대신 `final` 필드와 생성자를 사용하십시오.
3. **패키지 구조**:
   - `com.deepmirror.server.domain` (Entity 위치)
   - `com.deepmirror.server.repository`
   - `com.deepmirror.server.service`
   - `com.deepmirror.server.controller`
   - `com.deepmirror.server.dto`

## 3. 환경 설정
### 환경변수 파일 (.env)
**중요**: 프로젝트 실행 전 반드시 `.env` 파일을 생성해야 합니다.
1. `.env.example` 파일을 복사: `cp .env.example .env`
2. `.env` 파일에서 실제 값으로 수정:
   - `POSTGRES_DB`: 데이터베이스 이름 
   - `POSTGRES_USER`: 데이터베이스 사용자명 
   - `POSTGRES_PASSWORD`: DB 비밀번호 설정 
   - `SPRING_DATASOURCE_PASSWORD`: DB 비밀번호
   - `GEMINI_API_KEY`: Google AI Studio에서 발급받은 Gemini API 키
     - 발급 URL: https://aistudio.google.com/app/apikey
3. **주의**: `.env` 파일은 `.gitignore`에 포함되어 git에 커밋되지 않습니다.

### 설정 파일 (application.yml)
프로젝트는 `src/main/resources/application.yml`을 사용하여 Spring Boot 설정을 관리합니다.
- 데이터베이스 연결 정보
- JPA/Hibernate 설정 (ddl-auto: update)
- Gemini AI 모델 설정 (temperature: 0.7)
- Swagger UI 설정

## 4. 빌드 및 실행 명령어
### 빌드
- `chmod +x gradlew` (권한 없을 시)
- `./gradlew build -x test` (테스트 제외 빠른 빌드 - MVP 단계 권장)

### 실행
- **Docker (메인)**: `docker compose up -d --build` (DB + App 실행)
- **Log 확인**: `docker compose logs -f app`
- **DB 재설정**: `docker compose down -v` (데이터 초기화 필요시만 사용)

### 로컬 테스트 (단위 테스트)
- `./gradlew test`

## 5. 데이터베이스 설정
- **DB**: PostgreSQL 15 (Docker Service: `db`)
- **접속 정보**:
  - URL: `jdbc:postgresql://db:5432/deepmirror` (내부), `localhost:5432` (외부)
  - User: `${POSTGRES_USER}` (환경변수로 관리)
  - Password: `${POSTGRES_PASSWORD}`
- **JPA**: `ddl-auto: update` (자동 테이블 관리)

## 6. 아키텍처 및 개발 패턴
1. **Entity**: `domain/` 패키지에 `@Entity` 클래스 생성.
2. **Repository**: `repository/`에 `JpaRepository` 상속 인터페이스 생성.
3. **DTO**: Entity를 직접 반환하지 말고, `dto/` 패키지의 DTO 객체로 변환하여 반환.
4. **Service**: 비즈니스 로직은 `service/` 패키지에 구현. Constructor Injection 사용.
5. **Controller**: REST API와 View 렌더링을 담당.
   - `ResultController`: 성격 분석 결과 CRUD API
   - `FeedbackController`: 피드백 수집 API
   - `ViewController`: Thymeleaf 뷰 렌더링

## 7. 주요 기능
### 7.1 성격 분석 시스템
- **Entity**: `Result` (domain/Result.java)
- **기능**: 사용자 응답을 받아 Gemini AI로 성격 분석 수행
- **분석 항목**:
  - 기본 성격 특성 (personality)
  - 상세 점수 (detailed_scores) - JSON 형식으로 저장
- **API**: `/api/results` (POST, GET, PUT, DELETE)

### 7.2 피드백 시스템
- **Entity**: `Feedback` (domain/Feedback.java)
- **기능**: 사용자가 분석 결과에 대한 피드백 제공
- **수집 정보**: 닉네임, 정확도 평가, 코멘트
- **API**: `/api/feedback` (POST)

### 7.3 AI 서비스
- **Service**: `GeminiService` (service/GeminiService.java)
- **모델**: gemini-3-flash-preview
- **용도**: 사용자 응답 분석 및 성격 특성 도출

## 8. API 문서 (Swagger UI)
- **접속 URL**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- Spring Boot 실행 후 브라우저에서 Swagger UI로 API 테스트 가능

## 9. 보안 주의사항

### 9.1 개발 환경
- **절대 금지**: `.env` 파일을 git에 커밋하지 마십시오.
- **API 키 관리**: Gemini API 키는 반드시 `.env` 파일로 관리
- **기본 설정**: `.env.example`의 값들은 플레이스홀더이므로 실제 사용 시 반드시 변경

### 9.2 프로덕션 환경 (운영 배포 시)
- **비밀번호**: 강력한 비밀번호 사용 필수 (최소 16자 이상, 특수문자 포함)
- **데이터베이스 사용자**: 기본 사용자명 변경 권장
- **포트 보안**:
  - 애플리케이션 포트 8080을 방화벽으로 보호
  - PostgreSQL 포트 5432는 외부 노출 금지 (docker-compose.yml에서 이미 설정됨)
- **JPA ddl-auto**: 운영 환경에서는 `validate` 또는 `none`으로 변경
- **SSL/TLS**: HTTPS 적용 필수 (nginx 등 리버스 프록시 사용)
- **API 키 보안**:
  - Gemini API 키 정기적 갱신
  - 키 노출 시 즉시 폐기 및 재발급
- **환경 분리**: 개발/스테이징/프로덕션 환경별 별도 `.env` 파일 관리

### 9.3 Git 커밋 규칙
- **커밋 타이밍**: 사용자 요청 시에만 실행
- **커밋 메시지**: 한글로 변경사항을 간단하고 명확하게 설명
