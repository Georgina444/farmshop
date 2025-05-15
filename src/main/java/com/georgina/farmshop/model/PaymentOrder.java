package com.georgina.farmshop.model;
import com.georgina.farmshop.domain.PaymentMethod;
import com.georgina.farmshop.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;
    private PaymentMethod paymentMethod;
    private String paymentLinkId;

    @ManyToOne
    private User user;


    //  @JoinColumn(name = "order_id")  // FK in payment_order table
    @OneToMany
    private Set<Order> orders = new HashSet<>();  // a user can buy from different sellers
    // we have to create separate orders because the seller is different
}
