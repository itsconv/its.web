# 📝 Project Development Convention (v1.0)

본 문서는 프로젝트의 일관성을 유지하고 유지보수 효율을 높이기 위한 코드 작성 표준을 정의합니다.

---

## 1. 계층별 기본 CRUD 네이밍 규칙

가장 기본이 되는 CRUD 동작에 대해 계층별로 통일된 접두어를 사용합니다.

| 역할     | Controller (액션 중심) | Service (비즈니스 중심) | Repository (JPA 관례) |
|:-------| :--- | :--- | :--- |
| 조회 | `get...` | `find...` | `find...` / `exists...` |
| 생성 | `create...` | `register...` | `save...` |
| 수정   | `modify...` | `update...` | `save...` |
| 삭제   | `remove...` | `delete...` | `delete...` |

> 보조 규칙
> - 단건 vs 목록: 목록 조회 시에는 복수형(`...s`) 또는 `...List`를 붙여 의도를 명확히 합니다.
> - Repository: JPA Query Methods 명명 규칙을 최우선으로 따릅니다.

---

## 2. 상황별 확장 네이밍 규칙

단순 CRUD 외에 빈번하게 발생하는 비즈니스 상황에 대한 권장 네이밍입니다.

### 1) 검증 및 중복 확인
| 계층 | 권장 접두어 | 예시 | 설명 |
| :--- | :--- | :--- | :--- |
| **Controller** | `check...` | `checkEmailDuplicate()` | 사용 가능 여부 확인 요청 |
| **Service** | `is...` / `validate...` | `isEmailAvailable()` | 논리 판단 또는 예외 발생 |
| **Repository** | `existsBy...` | `existsByEmail()` | DB 존재 여부 확인 |

### 2) 파일 및 미디어 처리
| 계층 | 권장 접두어 | 예시 | 설명 |
| :--- | :--- | :--- | :--- |
| **Controller** | `upload...` / `download...` | `uploadProfile()` | 사용자 파일 전송 액션 |
| **Service** | `store...` / `load...` | `storeFile()` | 물리적 저장 및 로드 로직 |

### 3) 통계 및 집계
| 계층 | 권장 접두어 | 예시 | 설명 |
| :--- | :--- | :--- | :--- |
| **Controller** | `get...Stats` / `get...Count` | `getUserStats()` | 결과 데이터 중심 |
| **Service** | `calculate...` / `summarize...` | `calculateRevenue()` | 계산/요약 로직 수행 |
| **Repository** | `countBy...` | `countByStatus()` | JPA 관례 (Count 쿼리) |

### 4) 외부 연동 및 메시징
| 계층 | 권장 접두어 | 예시 | 설명 |
| :--- | :--- | :--- | :--- |
| Service | `send...` / `call...` | `sendAuthCode()` | 외부 시스템으로 전송 |
| Component | `fetch...` / `request...` | `fetchExternalData()` | 데이터를 가져오는 행위 |

---

## 3. 도메인 필드 네이밍 (Prefix 제거)

도메인 클래스 내부에서는 클래스명 반복형 접두어를 제거하여 간결함을 유지합니다.

| AS-IS (중복) | TO-BE (권장) |
| :--- | :--- |
| `historyId` | `id` |
| `historyName` | `name` |
| `historyParent` | `parent` |

> `history.getName()`은 이미 History의 이름임을 의미하므로 정보 중복을 피함.
> 
---

## 4. 예외 처리 및 공통 응답 규격

### 공통 API 응답 포맷 (JSON)
```json
{
  "status": 404,
  "code": "USER_NOT_FOUND",
  "message": "사용자를 찾을 수 없습니다.",
  "data": null
}
```

### 예외 처리 전략
- 전역 예외 처리 정책은 `BusinessException`을 공통으로 사용합니다. 
- `ErrorCode`는 공통 enum으로 운영하며, `code`는 `ErrorCode`의 enum 이름을 그대로 사용합니다.

---

*이 문서는 팀의 협의에 따라 지속적으로 업데이트됩니다.*
