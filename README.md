# Portfolio Backend

Spring Boot 기반 개인 포트폴리오 API 서버.

## Stack
- Java 17, Spring Boot 3.x
- Spring Data JPA, Spring Security
- MySQL 8, JWT

## Setup
1. MySQL 8 + `portfolio_db` 생성
2. 환경변수 설정: `DB_PASSWORD`, `ADMIN_USERNAME`, `ADMIN_PASSWORD`, `JWT_SECRET`
3. `./gradlew bootRun`
