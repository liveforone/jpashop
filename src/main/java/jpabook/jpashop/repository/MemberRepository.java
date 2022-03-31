package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /*
    select m from Member m where m.name = ?
    이라고 findBy뒤에 단어를 보고 spring data jpa가
    쿼리를 짜줌
     */
    List<Member> findByName(String name);
}
