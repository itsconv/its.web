# 예외 처리 가이드

본 문서는 프로젝트의 공통 예외 처리 정책과 응답 포맷 규칙을 정의합니다.

---

## 1. 목적

- 컨트롤러/서비스 전반에서 일관된 예외 처리 방식 유지
- 클라이언트가 파싱 가능한 고정 응답 포맷 제공
- 도메인 에러와 시스템 에러를 명확히 구분

---

## 2. 공통 응답 포맷

모든 예외 응답은 아래 JSON 구조를 따릅니다.

```json
{
  "status": 400,
  "code": "COMMON_BAD_REQUEST",
  "message": "요청 값이 유효하지 않습니다.",
  "data": null
}
```

- `status`: HTTP 상태 코드 정수값 (`HttpStatus.value()`)
- `code`: `ErrorCode` enum 이름 (`name()`)
- `message`: 사용자/운영자에게 보여줄 메시지
- `data`: 기본 `null` (필요 시 확장 가능)

---

## 3. 예외 계층

- 공통 비즈니스 예외: `BusinessException` (`RuntimeException` 상속)
- 코드/상태 정의: `ErrorCode` enum
- 전역 처리: `GlobalExceptionHandler` (`@RestControllerAdvice`)

역할 분리:
- 서비스/도메인: `BusinessException` 발생
- 전역 핸들러: `BusinessException` -> `ApiResponse` 매핑

---

## 4. 메시지 우선순위 규칙

`BusinessException` 처리 시 메시지는 아래 순서를 따릅니다.

1. `BusinessException`에 전달한 커스텀 메시지
2. `ErrorCode` 기본 메시지

예시:

```java
// 기본 메시지 사용
throw new BusinessException(ErrorCode.COMMON_NOT_FOUND);

// 커스텀 메시지 사용
throw new BusinessException(ErrorCode.COMMON_NOT_FOUND, "사용자 ID 123을 찾을 수 없습니다.");
```

응답 예시:

```json
{
  "status": 404,
  "code": "COMMON_NOT_FOUND",
  "message": "사용자 ID 123을 찾을 수 없습니다.",
  "data": null
}
```

---

## 5. 현재 전역 처리 범위

- `BusinessException` -> `ErrorCode` 기반 응답
- `MethodArgumentNotValidException` -> `COMMON_BAD_REQUEST`
- `Exception` -> `COMMON_INTERNAL_SERVER_ERROR`

---

## 6. 성공 응답 규칙

성공 응답도 동일한 공통 포맷을 사용합니다.

```json
{
  "status": 200,
  "code": "COMMON_SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "id": 1
  }
}
```

기본 사용:

```java
return ResponseEntity.ok(ApiResponse.success(data));
```

커스텀 성공 코드/메시지 사용:

```java
return ResponseEntity.status(HttpStatus.CREATED).body(
    ApiResponse.success(
        HttpStatus.CREATED.value(),
        SuccessCode.USER_CREATED.name(),
        SuccessCode.USER_CREATED.getMessage(),
        data
    )
);
```

참고:
- 공통 성공 코드는 `SuccessCode` enum 사용
- HTTP 상태코드는 `HttpStatus` 사용(숫자 하드코딩 금지)
- 필요 시 API 단위로 `code`/`message`를 직접 지정 가능

---

## 7. 작성 규칙

- 비즈니스 실패는 `BusinessException`만 사용
- `code` 문자열 하드코딩 금지 (`ErrorCode` 사용)
- `status`는 문자열(`SUCCESS`, `ERROR`) 사용 금지, 정수만 사용
- 신규 도메인 에러는 `ErrorCode`에 추가 후 사용
