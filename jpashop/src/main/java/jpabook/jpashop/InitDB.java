package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;


/*
*   userA
*       JPA1 BOOK
*       JPA2 BOOK
*   userB
*       SPRING1 BOOK
*       SPRING2 BOOK
* */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        private Member createMember(String user_name, String city){
            Member member = new Member();
            member.setName(user_name);
            member.setAddress(new Address(city, "1", "1111"));
            return member;
        }

        private Book createBook(String book_name, int price, int count){
            Book book = new Book();
            book.setName(book_name);
            book.setPrice(price);
            book.setStockQuantity(count);
            return book;
        }

        public void dbInit1(){
            Member member = createMember("userA", "city1");
            em.persist(member);

            Book book = createBook("JPA1 BOOK", 10000, 100);
            Book book2 = createBook("JPA2 BOOK", 20000, 100);

            em.persist(book);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Dilivery dilivery = new Dilivery();
            dilivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, dilivery, orderItem1, orderItem2);
            em.persist(order);

        }

        public void dbInit2(){
            Member member = createMember("userB", "city2");
            em.persist(member);

            Book book = createBook("SPRING1 BOOK", 20000, 200);
            Book book2 = createBook("SPRING2 BOOK", 40000, 300);

            em.persist(book);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Dilivery dilivery = new Dilivery();
            dilivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, dilivery, orderItem1, orderItem2);
            em.persist(order);

        }
    }
}

