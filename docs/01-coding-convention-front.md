# 📝 Frontend Coding Convention (v1.0)

본 문서는 프로젝트의 일관성을 유지하고 유지보수 효율을 높이기 위한 코드 작성 표준을 정의합니다.

---

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