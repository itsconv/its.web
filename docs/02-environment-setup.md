# 환경 실행 가이드

## 1. 도커 컴포즈 실행 가이드

로컬 개발 환경(`local` 프로필)에서만 Docker를 사용하여 데이터베이스를 구동합니다. 프로젝트 루트 디렉토리에서 아래 명령어를 사용하세요.

[실행 명령어]
- DB 실행 (백그라운드): docker-compose up -d
- DB 중지 및 컨테이너 삭제: docker-compose down

※ 주의:
- 이 설정은 'local' 프로필 실행 시에만 해당됩니다.
- DB 데이터는 'mariadb_data' 볼륨으로 관리되어 컨테이너 삭제 후에도 보존됩니다.
- 'dev', 'prod' 환경은 별도의 외부 데이터베이스 서버를 사용하므로 이 명령어를 사용하지 않습니다.

---

## 2. 애플리케이션 실행 가이드

`application-{profile}.yml` 설정에 따라 환경별로 애플리케이션을 구동합니다.

| 실행 환경 (Profile) | 실행 명령어 | 비고 |
| :--- | :--- | :--- |
| **Local** | ./gradlew bootRun | 로컬 DB(Docker) 연동 및 개발 |
| **Dev** | SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun | 개발 서버 DB 연동 및 테스트 |
| **Prod** | SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun | 운영 서버 DB 연동 및 실서비스 |

---

## 3. 핵심 확인 및 주의사항

### 3.1 JPA ddl-auto 운영 정책
데이터 유실 방지를 위해 환경별 설정을 엄격히 구분합니다.

- **Local/Dev:** `update` (엔티티 변경 사항을 스키마에 자동 반영)
- **Prod (운영):** `validate` (엔티티-테이블 매핑 검증만 수행, 변경 금지)
  * **중요:** 운영 환경에서 `create`, `update` 등을 사용하면 데이터 삭제 위험이 크므로 반드시 `validate` 설정을 유지해야 합니다.

### 3.2 트러블슈팅
DB 접속 에러(SQLNonTransientConnectionException 등) 발생 시 점검 항목:

1. **Local 환경:** `docker ps`로 MariaDB 컨테이너가 정상적으로 `Up` 상태인지 확인합니다.
2. **Dev/Prod 환경:** 각 환경의 외부 DB 서버 네트워크 상태 및 방화벽 설정을 확인합니다.
3. **공통:** 활성화된 프로필(yml)의 `spring.datasource` 정보(URL, ID/PW)가 실제 DB 정보와 일치하는지 확인합니다.
4. **정상 로그:** 구동 성공 시 로그에서 `HikariPool - Start completed.`와 JPA 초기화 완료 문구를 확인합니다.