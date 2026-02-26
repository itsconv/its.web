# 화면별 레이아웃 재정의 (Page-Specific Layout Overrides)

## 현재 레이아웃 구성
- 공통 레이아웃: `src/main/resources/templates/layouts/admin-layout.html`
- 상단바/탭: `src/main/resources/templates/fragments/header.html`
- 사이드바: `src/main/resources/templates/fragments/sidebar.html`
- 개별 화면(`basic`, `bbs`, `year` 등): `layout:fragment="page-content"` 본문만 정의

## 렌더링 구조
1. 각 화면 템플릿은 `layout:decorate="~{layouts/admin-layout}"`를 사용합니다.
2. `admin-layout.html`에서 `header`, `sidebar`, `footer`를 프래그먼트로 조합합니다.
3. breadcrumb와 본문 상단 제목/설명은 레이아웃에서 공통 렌더링합니다.
4. 페이지별 HTML은 테이블/폼 등 실제 컨텐츠만 담당합니다.

## `AdminPageDataAdvice`에서 가져오는 값
- 위치: `src/main/java/com/itsconv/web/admin/advice/AdminPageDataAdvice.java`
- 적용 범위: `@ControllerAdvice(assignableTypes = AdminViewController.class)`
- URI 기준으로 `AdminMenu.fromUri(uri)`를 조회한 뒤, 아래 모델 속성을 공통 주입합니다.
- `pageData`
- `activeTab`
- `breadcrumbTitle`
- `pageTitle`
- `pageDescription`

## `AdminMenu` 기준 매핑
- 위치: `src/main/java/com/itsconv/web/admin/advice/AdminMenu.java`
- 각 메뉴별로 아래 메타데이터를 보관합니다.
- URI 목록(`uris`)
- 페이지 식별자(`pageData`)
- 활성 탭(`activeTab`)
- breadcrumb 제목(`breadcrumbTitle`)
- 본문 제목(`pageTitle`)
- 본문 설명(`pageDescription`)
- 매핑 실패 시 기본값은 `ADMIN_LIST`입니다.

## 화면별 active 처리 방식
- 상단 탭 active: `header.html`에서 `pageData` 조건으로 `th:classappend` 적용
- 사이드바 active: `sidebar.html`에서 `activeTab`/`pageData` 조건으로 `th:classappend` 적용
- 기존 JS 기반 메뉴 구성 파일(`admin-common.js`, `admin-shared-config.js`)은 제거되었습니다.
