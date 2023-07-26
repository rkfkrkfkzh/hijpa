
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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원A");
            member1.setTeam(teamA);
            em.persist(member1);
            ;

            Member member2 = new Member();
            member2.setUsername("회원B");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원C");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원A")
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member);

            }
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
