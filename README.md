### JPQL 문법
+ 엔티티와 속성은 대소문자 구분 O (Member, age)
+ JPQL 키워드는 대소문자 구분 X (SELECT, FROM, where)
+ 엔티티 이름 사용, 테이블 이름이 아님(Member)
+ 별칭은 필수(m) (as는 생략가능)
***
### 결과 조회 API
+ query.getResultList(): 결과가 하나 이상일때, 리스트 반환 (결과가 없으면 빈리스트 반환)
+ query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환 (결과가 없으면 : javax.persistence.NoResultException, 둘 이상이면 : javax.persistence.NonUniqueResultException)

