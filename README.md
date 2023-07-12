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

