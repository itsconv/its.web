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
- Hierarchy: `BusinessException`을 상속받는 도메인별 예외(`UserException` 등) 구조를 가집니다.
- Error Code: Enum 형식을 사용하여 에러 코드를 관리하며, Enum 이름을 `code`로 사용합니다.

---

## 5. 프론트단 코딩 컨벤션 제안

# 1. AJAX는 Promise 기반 문법 지향

- `success`, `error` 콜백 방식 지양
- Promise 기반 (`.then()`, `.catch()`) 또는 `async/await` 사용

---

```javascript
$.ajax({
  url: '/api/user',
  method: 'GET'
})
.then(function (res) {
  console.log(res.data);
})
.catch(function (err) {
  console.error(err);
});
```

---

# 2. const, let 사용 지향 (var 사용 지양)

- `var` 사용 금지
- 기본은 `const`
- 재할당 필요 시 `let`

---

```javascript
const API_URL = '/api/user';

let count = 0;
count += 1;
```

```javascript
const user = {
  name: 'John'
};

user.name = 'Mike'; // 내부 값 변경은 가능

user = {name: 'Mike'} // 재할당 X
```

---

# 3. 향상된 for문 사용 지향

- 전통적인 index for문 지양
- 배열은 `for...of`
- 객체는 `Object.keys()` 권장

---

```javascript
const arr = [1, 2, 3];

for (const value of arr) {
  console.log(value);
}
```

```javascript
const obj = { a: 1, b: 2 };

Object.keys(obj).forEach(key => {
  console.log(key, obj[key]);
});
```

---

# 4. 템플릿 리터럴(`) 사용

- 문자열 연결 대신 템플릿 리터럴 사용

---

## 템플릿 리터럴

```javascript
const msg = `이름은 ${name} 입니다.`;
```

---

# 5. 이벤트 핸들러에서 this 사용시, 화살표 함수 사용 지양

```javascript
$('.btn').on('click', function (e) {
  console.log(this); // 클릭한 요소
});
```

---

# 6. 네이밍 규칙

- class → kebab-case
- id → camelCase

---

```html
<div id="userList" class="user-list-item active-item"></div>
```

```javascript
$('#userList');
$('.user-list-item');
```
---

# 7. 체이닝 사용 시 줄바꿈

- 선택자에서 메서드 2개 이상 체이닝 시 줄바꿈

---

## 한 줄 방식

```javascript
$('#userList').find('.item').addClass('active').fadeIn();
```

---

## 줄바꿈 방식

```javascript
$('#userList')
  .find('.item')
  .addClass('active')
  .fadeIn();
```
---

*이 문서는 팀의 협의에 따라 지속적으로 업데이트됩니다.*