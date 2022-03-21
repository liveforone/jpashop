package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {  //아이템이 없으면
            em.persist(item);  //저장해주고
        } else {  //아이템이 있다면
            em.merge(item);  //머지, 즉 업데이트 해줘라
        }
    }

    public Item findOne(Long id) {  //단건조회
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {  //전체조회 따라서 리스트로!!
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
