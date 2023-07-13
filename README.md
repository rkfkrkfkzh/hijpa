### JPQL 문법
+ 엔티티와 속성은 대소문자 구분 O (Member, age)
+ JPQL 키워드는 대소문자 구분 X (SELECT, FROM, where)
+ 엔티티 이름 사용, 테이블 이름이 아님(Member)
+ 별칭은 필수(m) (as는 생략가능)
***
### 결과 조회 API
+ query.getResultList(): 결과가 하나 이상일때, 리스트 반환 (결과가 없으면 빈리스트 반환)
+ query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환 (결과가 없으면 : javax.persistence.NoResultException, 둘 이상이면 : javax.persistence.NonUniqueResultException)
***
### 프로젝션
+ SELECT 절에 조회할 대상을 지정하는 것
+ 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타입)
  + SELECT m FROM Member m -> 엔티티 프로젝션
  + SELECT m.team FROM Member m -> 엔티티 프로젝션
  + SELECT m.address Member m -> 임베디드 타입 프로젝션
  + SELECT m.username, m.age Member m -> 스칼라 타입 프로젝션
+ DISTINCT로 중복 제거
***
### 프로젝션 - 여러 값 조회
+ SELECT m.username, m.age FROM Member m
+ Query 타입으로 조회 
+ Object[] 타입으로 조회 
+ new 명령어로 조회 
+ 단순값은 DTO로 바로 조회 SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
+ 패키지 명을 포함한 전체 클래스 명 입력
+ 순서와 타입이 일치하는 생성자 필요
---
### 페이징 API
+ JPA는 페이징을 다음 두 API로 추상화
+ setFirstResult(int startPosition) : 조회 시작 위치(index)
+ setMaxResults(int maxResult) : 조회할 데이터 수
---
### 조인
+ 내부 조인 : SELECT m FROM Member m [INNER] JOIN m.team t
+ 외부 조인 : SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
+ 세타 조인 : select count(m) from Member m, Team t where m.username = t.name
---
### 조인 대상 필터링
+ 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
  + JPQL : SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
  + SQL : SELECT m.*,t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name = 'A'
---
### JPA 서브 쿼리 한계
+ JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
+ SELECT절도 가능(하이버네이트에서 지원)
+ FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
  + 조인으로 풀 수 있으면 풀어서 해결
---
### JPQL 타입 표현
+ 문자 : 'HELLO', 'She''s'
+ 숫자 : 10L(Long), 10D(Double), 10F(Float)
+ Boolean : TRUE, FALSE
+ ENUM : jpabook.MemberType.Admin(패키지명 포함)
+ 엔티티 타입 : TYPE(m) = Member (상속 관계에서 사용)

+ ![](../../../../var/folders/nk/3h97h_0j7mz8bpg19t1vtf3w0000gn/T/TemporaryItems/NSIRD_screencaptureui_smbfar/스크린샷 2023-07-13 오후 9.19.39.png)

