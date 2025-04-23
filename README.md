# server-tech

서버 개발에 필요한 기술들을 경험해보고 프로젝트에 적용하기 위한 레포지토리입니다.

기술 공부에 초점을 맞추고, 도메인과 구조는 최대한 간단하게 설정했습니다.

## 프로젝트 설명

간단한 게시판 기능을 할 수 있는 서버입니다.

사용자는 일반 사용자와 관리자로 나뉩니다.

- 일반 사용자
    - 게시글 CRUD
    - 게시글에 댓글 CRUD
    - 게시글과 댓글에 좋아요 (누르기/취소)
- 관리자
    - 일반 사용자 탈퇴
    - 게시글/댓글 강제 삭제

## 도메인 구조

![photo](/.github/photos/erd.png)

## 적용 기술

- 기본
    - Java 17
    - SpringBoot 3.x.x
- 인증 인가
    - Spring Security 6.x.x
    - JWT
- 문서화
    - Swagger
- DB / ORM
    - PostgreSQL
    - JPA

## 추가 설정
### profile

```local```

### 환경 변수 (**BOLD**로 표시된 부분은 필수로 설정)

- DB 설정
  - DB_HOST=location_of_DB (localhost)
  - DB_PORT=port_number_of_DB_process (5432)
  - DB_NAME=name_of_database
- JWT 설정
  - JWT_ISSUER=any_value_can_represent_you (example@email.com)
  - **JWT_KEY=any_value_encoded_by_BASE64_is_possibles**
- Redis 설정
  - REDIS_HOST=location_of_redis (localhost)
  - REDIS_PORT=port_number_of_redis_process (6379)

### Redis 추가

Redis Stream을 적용해서 Event를 다룹니다.

service 계층의 비즈니스 로직에서 이벤트를 발생시켜 알림 객체를 생성하도록 만들었습니다. 

## Docker로 실행하기

### 1. .env 추가
- .env 파일을 루트 폴더에 생성 후 위 환경변수를 참고해 변수를 넣습니다.
- 이때 아래의 정보는 필수적으로 넣어줍니다.
  ```
    JWT_KEY=이 부분은 BASE64로 인코딩된 충분히 긴 문자열을 넣으시면 됩니다 
    DB_HOST=postgres-db-server
    REDIS_HOST=redis-cache-server
  ```

### 2. 실행
- 아래의 명령어로 docker compose 파일을 실행합니다.
- 이때 compose.yml 파일과 같은 위치에서 실행합니다.
  
  ```$ docker compose up -d --build```

### 3. 종료
- 아래의 명령어로 종료합니다.

  ```$ docker compose down```