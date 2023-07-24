
package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);
            ;

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            //language=HQL
            String query = "select m.username from Team t join t.members m"; // FROM절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색가능

            List<String> resultList = em.createQuery(query, String.class).getResultList();
            System.out.println("resultList = " + resultList);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
