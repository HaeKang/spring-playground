package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Dilivery {
    @Id @GeneratedValue
    @Column(name="delilvery_id")
    private Long id;

    @OneToOne(mappedBy = "dilivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum type 시 enumerated 어노테이션 추가, enumtype은 꼭 string으로
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;  // READY, COMP
}

