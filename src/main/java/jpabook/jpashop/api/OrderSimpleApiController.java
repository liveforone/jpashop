package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    /*
    xToOne관계 (ManyToOne, OneToOne)
    Order
    Order -> Member
    Order -> Delivery
     */

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")  //엔티티 직접 노출
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();  //LAZY 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {  //엔티티 DTO로 변환
        return orderRepository.findAllByString(new OrderSearch()).stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
    }  //쿼리 5번 나감

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {  //엔티티 DTO로 변환 - 페치조인
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }  /*
    v2와 v3의 로직은 같으나 쿼리가 완전히 다르다. v3는 jpql의 페치 조인을 사용함
    쿼리 1번 나감
    재사용성이 높음
    */

    @GetMapping("/api/v4/simple-orders")  //DTO 바로 조회
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
