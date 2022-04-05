package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // n:1
    @JoinColumn(name="member_id")   // fk
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delilvery_id")
    private Dilivery dilivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 ORDER, CANCEL


    // 연관관계 편의 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Dilivery dilivery){
        this.dilivery = dilivery;
        dilivery.setOrder(this);
    }

    // == 생성 메서드 ==
    public static Order createOrder(Member member, Dilivery dilivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(dilivery);

        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);

        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // == 비즈니스 로직 == ~> 도메인 모델 패턴 (엔티티가 비즈니스 로직을 가지고 객체지향 특성 적극 활용) <-> 트랜잭션 스크립트 패턴 (서비스 계층에서 대부분의 비즈니스 로직 처리)

    // 주문취소
    public void cancel(){
        // 이미 배송 완료
        if (dilivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료 -> 취소 불가능");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    // == 조회 로직 ==

    // 전체 주문 가격
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
