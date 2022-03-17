package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }  //간단하게 아이디 정도만 조회하도록

    public Member find(Long id) {  //조회하는 메서드
        return em.find(Member.class, id);
    }

}
