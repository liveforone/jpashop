package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

    @PersistenceContext
    private final EntityManager em;
    
    public void save(Member member) {
        em.persist(member);
    }
    
    public Member findOne(Long id) {  //단건조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {  //전부조회, 전부조회는 리스트로!!!
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {  //이름으로 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)  //파라미터 바인딩
                .getResultList();
    }
}
