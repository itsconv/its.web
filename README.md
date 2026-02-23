# API 정리 (ITSConvergence Admin)

## 1) 화면별 API (현재 기준)

현재 애플리케이션에서 컨트롤러는 화면 렌더링용 GET 엔드포인트만 제공합니다.  
아래는 페이지 라우트별 정리입니다.

| 화면 구분 | Route (GET) | Template | JS 진입점 | 비고 |
|---|---:|---|---|---|
| 기본 홈(관리자) | `/` | `basic/admin_list`로 redirect | - | 초기 진입 시 기본 화면으로 리다이렉트 |
| 관리자설정 | `/basic/admin_list` | `basic/admin_list.html` | `/js/admin_list.js` | 관리자 목록 화면 |
| 팝업관리 | `/basic/popup_list` | `basic/popup_list.html` | `/js/popup_list.js` | 팝업 목록/관리 |
| 홍보자료 | `/bbs/data` | `bbs/data.html` | `/js/data.js` | 홍보자료(프로모션) 목록 |
| 공지사항 | `/bbs/its_notice` | `bbs/its_notice.html` | `/js/its_notice.js` | 공지사항 목록 |
| 자료실 | `/bbs/download` | `bbs/download.html` | `/js/download.js` | 자료실 목록 |
| ITS Story | `/bbs/story` | `bbs/story.html` | `/js/story.js` | ITS Story 목록 |
| 게시판 관리(공통/테스트) | `/bbs/bbs_list` | `bbs/bbs_list.html` | `/js/bbs_list.js` | 공통 게시판 목록 화면 |
| 연혁관리 | `/year/year_list` | `year/year_list.html` | `/js/year_list.js` | 연혁 목록 |

## 2) 공통 API 호출 현황

- 현재 화면 스크립트는 내부 더미데이터를 사용하며, 서버 통신 API 호출(fetch/XHR/ajax)은 사용하지 않습니다.
- 즉, **화면별 CRUD API 구현은 아직 미진행 상태**입니다.
