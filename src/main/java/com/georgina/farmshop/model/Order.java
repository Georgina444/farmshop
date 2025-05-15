package com.georgina.farmshop.model;

import com.georgina.farmshop.domain.OrderStatus;
import com.georgina.farmshop.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;

    @ManyToOne
    private User user;

    private Long sellerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @ManyToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private double totalMrpPrice;
    private Integer totalSellingPrice;

    private Integer discount;

    private OrderStatus orderStatus;
    private int totalItems;

    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private LocalDateTime dateOrdered = LocalDateTime.now();
    private LocalDateTime dateDelivered = dateOrdered.plusDays(2);


}
