# SpringBoot - Project - Anytime
스프링 부트 + 커뮤니티 사이트
<br>
<br>

## 🖥️ 프로젝트 소개
에브리타임 클론 코딩하여 만든 커뮤니티 사이트입니다.
<br>
<br>

## 📆개발 기간
* 23.09.19 ~ 23.11.06
<br>

### 🧑‍🤝‍🧑멤버 구성
  - 이윤희 : 커뮤니티 페이지, 마이 페이지, 관리자 페이지(게시판 승인/신고 관리), 댓글 CRUD, 게시판 생성
  - 이종옥 : 게시판 읽기/수정/삭제, 캘린더 CRUD, HOT&BEST 게시판, 게시물 리스트 조회/검색, 스크랩, 신고, 쪽지
  - 조상운 : 게시물 작성/수정/삭제, 게시물 공감, 시간표 CRUD
  - 차지원 : 회원가입, 로그인&로그아웃, 웹메일 인증, 학점 계산기 CRUD
<br>

### ⚙️개발 환경
- java
- IDE : STS
- Framework : SpringBoot
- Database : Oracle 11
- ORM : Mybatis
<br>

## 📌주요 기능
#### 메인 페이지
- 학교 검색

#### 로그인
- ID/비밀번호 찾기
- 로그인 유지
- 로그아웃
- CAPTCHA

#### 회원가입
- 주소 API 연동
- ID 중복 체크
- 이메일 본인 인증

#### 커뮤니티 페이지
- 학교 별 컨텐츠 출력
- 인증 여부에 따라 VIEW 분류


#### 마이 페이지
- 학교 인증
- 이메일 본인 인증
- 회원정보 변경
- 주소 API 연동
- 회원 탈퇴

#### 캘린더
- FullCalendar API 연동
- 일정 CRUD
  
#### 게시판
- 게시판 생성 요청
- 게시판 읽기, 수정, 삭제

#### 게시물
- 게시물 CRUD
- 게시물 공감, 스크랩 기능
- HOT/BEST 게시물(공감수)
  
#### 댓글
- 계층형 댓글 로직
- 댓글 CRUD

#### 신고
- 게시물 신고
- 댓글 신고

#### 쪽지
- 쪽지 발송, 읽

#### 학점 계산기
- Chart.js API 연동
- 과목 별 학점 CRUD
  
#### 시간표
- Canvas API 연동
- 시간표 CRUD
- 수업 생성, 읽기, 삭제

#### 관리자 페이지
- 부트스트랩 사용(대시보드)
- 구글 Chart, Chatr.js API 사용
- 게시판 승인 처리
- 게시판 검색
- 신고 조회
- 신고 처리
  - 게시물/댓글 비활성화
  - 사용자 정지
- 공지사항

