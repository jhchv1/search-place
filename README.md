# 장소 검색 서비스
이 프로젝트는 카카오의 `키워드로 장소 검색` API를 활용하여 구현되었습니다.

## 기본 요구사항
### 로그인
- `data.sql` 파일을 통해 애플리케이션 실행 시점에 사용자 데이터를 생성합니다.
- 초기 등록되어 있는 사용자명은 `jhchv`, 비밀번호는 `pass`를 `bcrypt` 암호화해서 저장했습니다.
- `data.sql` 파일에 아래 예시와 같이 사용자를 쉽게 추가할 수 있습니다.
```sql
insert into user (username, password) values ('jhchv1', '{noop}pass');
```

### 장소 검색
- 키워드를 통해 장소를 검색할 수 있도록 구현했습니다.

### 검색된 장소의 상세 조회
- 검색된 결과를 클릭하면 지번, 도로명주소, 전화번호 등의 상세 정보를 확인할 수 있습니다.
- 상세 정보에는 카카오맵 바로가기 링크와 함께 지도를 제공합니다. 지도에는 커스텀 오버레이를 띄우고 장소 이름과 주소를 표기하도록 구현했습니다.

### 내 검색 히스토리
- 나의 검색 히스토리를 최신순으로 보여주도록 구현했습니다.

### 인기 키워드 목록
- 사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드를 제공하도록 구현했습니다.
- 데이터가 많은 경우 호출시마다 인기 키워드 10개를 구하기에 부하가 있을것으로 판단되어, 30초마다 배치를 통해 인기 키워드 10개를 집계해두고 호출이 들어올때는 집계된 데이터를 내려주기만 하는 방향으로 구현했습니다.

## 프로젝트 구현에 사용된 오픈소스

| 사용된 오픈소스                     | 사용 목적 |
|-------------------------------------|-----------|
| spring-boot-starter-web             | 웹 서비스를 구현하기 위해 사용 |
| spring-boot-starter-data-jpa        | ORM 기반의 개발 패턴을 위해 사용 |
| spring-boot-starter-security        | 웹 서비스의 보안적 측면 관리를 위해 사용 |
| spring-boot-starter-thymeleaf       | 템플릿 엔진으로 `Thyemelaf`를 선택 |
| thymeleaf-extras-springsecurity5    | `Thymeleaf`와 `Spring Security`의 연동을 위해 사용 |
| bootstrap                           | 프론트엔드 디자인 라이브러리로 선택 |
| webjars-locator                     | `webjars` 하위 라이브러리의 용이한 버전 관리를 위해 사용 |
| spring-boot-devtools                | `Spring Boot` 기반 프로젝트 개발 단계에서 편리함을 위해 사용 |
| h2                                  | 메모리 DB로 `h2` 선택 |
| spring-boot-configuration-processor | `@ConfigurationProperties` 사용시 프로퍼티 자동 완성을 위해 사용 |
| lombok                              | `Getter`, `Setter` 생성 등의 기능을 편리하게 만들기 위해 사용 |
| spring-boot-starter-test            | `Spring Boot` 프로젝트의 테스트를 위해 사용 |
| spring-security-test                | `Spring Security` 프로젝트의 테스트를 위해 사용 |
