# 시큐리티 및 인증 상태 API 정리

본 문서는 현재 적용된 시큐리티 구성과 인증 상태 조회 API(`/api/auth/status`) 응답을 정리합니다.

---

## 1. 시큐리티 구성 요약

- 인증 방식: 세션 기반 `formLogin`
- 로그인 성공 기본 이동: `/basic/admin_list`
- 로그아웃: `/logout` -> `/login?logout`
- 세션 정책: `SessionCreationPolicy.IF_REQUIRED`
- 동시 세션: 사용자당 최대 1개
- CSRF: 비활성화 (`csrf.disable`)
> 추후 CSRF 설정 필요
- 인증 없이 허용 경로:
  - `/login`
  - `/error`
  - `/api/auth/status`
  - `/css/**`, `/js/**`, `/asset/**`, `/favicon.ico`

---

## 2. 사용자 인증 정보 로딩

- `CustomUserDetailsService`에서 `UserRepository.findByUserId(String userId)`로 사용자 조회
- 사용자 미존재 시 `UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.message)` 발생
- 인증 주체(`UserPrincipal`) 권한은 `ROLE_ADMIN` 고정 부여

---

## 3. 인증 상태 조회 API

- Method: `GET`
- URL: `/api/auth/status`
- 설명: 현재 세션이 로그인 상태인지 확인하고, 로그인 사용자 기본 정보를 반환
- 응답 포맷: `ApiResponse<AuthStatusResponse>`

### 3.1 로그인 상태 응답 예시

```json
{
  "status": 200,
  "code": "COMMON_SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "loggedIn": true,
    "userSeq": 1,
    "userId": "admin",
    "name": "관리자",
    "authorities": [
      "ROLE_ADMIN"
    ]
  }
}
```

### 3.2 미로그인 상태 응답 예시

```json
{
  "status": 200,
  "code": "COMMON_SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "loggedIn": false,
    "userSeq": null,
    "userId": null,
    "name": null,
    "authorities": []
  }
}
```

---

## 4. 참고

- 현재 `/api/auth/status`는 `permitAll` 경로이므로 비로그인 상태에서도 호출 가능
- `UserPrincipal` 응답에서 비밀번호 등 민감정보는 반환하지 않음
