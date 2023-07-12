
package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("lim");
            member.setAge(18);
            em.persist(member);

            Member result = em.createQuery("select m from Member m where m.username=:username", Member.class)
                    .setParameter("username", "lim")
                    .getSingleResult();
            System.out.println("result = " + result.getAge());

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
