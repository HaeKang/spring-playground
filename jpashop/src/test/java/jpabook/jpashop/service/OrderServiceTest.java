package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @PersistenceContext
    EntityManager em;


    @Test
    public void 주문() throws Exception {
        // Given
        Member member = createMember();
        Item item = createBook("에휴우우우", 10000, 10); // 이름, 가격, 재고
        int orderCount = 2;

        // When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");

        assertEquals(1, getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");

        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량 이다.");

        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        // Given
        Member member = createMember();
        Item item = createBook("하아아아아", 10000, 10); // 이름 가격 재고

        int orderCount = 11;    // 재고보다 많은 수량

        // When
        orderService.order(member.getId(), item.getId(), orderCount);   // 재고보다 많은 수량 주문

        // Then
        fail("재고 수량 부족 예외 발생해야함.");

    }

    @Test
    public void 주문취소(){
        // Given
        Member member = createMember();
        Item item = createBook("아아아ㅏ", 10000, 10);  // 이름 가격 재고
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);    // 주문

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소 시 상태는 CANCEL 이다.");

        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

    }

    private Member createMember(){
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}