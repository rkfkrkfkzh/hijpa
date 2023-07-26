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
---
### 조건식 - CASE 식
+ 기본 CASE 식
<img width="512" alt="스크린샷 2023-07-13 오후 9 24 51" src="https://github.com/rkfkrkfkzh/jpashop/assets/86057607/455412ec-fb38-45f9-89d0-975451e5bfab">
+ 단순 CASE 식
<img width="512" alt="스크린샷 2023-07-13 오후 9 31 10" src="https://github.com/rkfkrkfkzh/jpashop/assets/86057607/82302ba3-1df8-4913-aadd-69484bc4d120">
+ COALESCE : 하나씩 조회해서 null이 아니면 반환
  + 사용자 이름이 없으면 이름 없는 회원을 반환(아래참조)
  + select coalesce(m.username,'이름 없는 회원') from Member m
+ NULLIF : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
  + 사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인의 이름을 반환(아래참조)
  + select nullif(m.username, '관리자') from Member m
---
### 경로 표현식 특징
+ 상태 필드(state field) : 경로 탐색의 끝, 탐색 X
+ 단일 값 연관 경로 : 묵시적 내부조인(inner join)발생, 탐색 X
+ 컬렉션 값 연관 경로 : 묵시적 내부조인 발생, 탐색 X
  + FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
---
### 경로 탐색을 사용한 묵시적 조인 시 주의사항
+ 항상 내부 조인
+ 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
+ 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM(JOIN)절에 영향을 줌
---
### 페치 조인과 일반 조인의 차이
+ JPQL은 결과를 반환할 때 연관관계 고려 X
+ 단지 SELECT 절에 지정한 엔티티만 조회할 뿐
+ 여기서는 팀 엔티티만 조회하고, 회원 엔티티는 조회 X
+ 페치조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
+ 페치조인은 객체 그래프를 SQL 한번에 조회하는 개념
---
### 페치 조인 특징과 한계
+ 페치 조인 대상에는 별칭을 줄 수 없다.
  + 하이버네이트는 가능, 가급적 사용하지말자
+ 둘 이상의 컬렉션은 페치 조인 할 수 없다.
+ 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
  + 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
  + 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)
+ 연관된 엔티티들을 SQL 한 번으로 조회 - 성능 최적화
+ 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
  + @OneToMany(fetch = FetchType.LAZY) 글로벌 로딩 전략
+ 실무에서 글로벌 로딩 전략은 모두 지연 로딩
+ 최적화가 필요한 곳은 페치 조인 적용
