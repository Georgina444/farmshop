package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.domain.OrderStatus;
import com.georgina.farmshop.domain.PaymentStatus;
import com.georgina.farmshop.model.*;
import com.georgina.farmshop.repository.AddressRepository;
import com.georgina.farmshop.repository.OrderItemRepository;
import com.georgina.farmshop.repository.OrderRepository;
import com.georgina.farmshop.service.OrderService;
import com.georgina.farmshop.service.SellerReportService;
import com.georgina.farmshop.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final AddressRepository addressRepository;
  private final OrderItemRepository orderItemRepository;
  private final SellerService sellerService;
  private final SellerReportService sellerReportService;

  @Override
  public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {

    // check if the user has the shipping address saved on his profile
    if (!user.getAddresses().contains(shippingAddress)) {
      user.getAddresses().add(shippingAddress);
    }
    Address address = addressRepository.save(shippingAddress);

    // we have to create a different order for each seller in case the customer orders from different sellers

    Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream()
        .collect(Collectors.groupingBy(item -> item.getProduct()
            .getSeller().getId()));

    // empty set of orders
    Set<Order> orders = new HashSet<>();

    for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
      Long sellerId = entry.getKey();
      List<CartItem> items = entry.getValue();

      int totalOrdersPrice = items.stream().mapToInt(
          CartItem::getSellingPrice
      ).sum();
      int totalItems = items.stream().mapToInt(CartItem::getQuantity).sum();

      Order createdOrder = new Order();
      createdOrder.setUser(user);
      createdOrder.setSellerId(sellerId);
      createdOrder.setTotalMrpPrice(totalOrdersPrice);
      createdOrder.setTotalSellingPrice(totalOrdersPrice);
      createdOrder.setTotalItems(totalItems);
      createdOrder.setShippingAddress(address);
      createdOrder.setOrderStatus(OrderStatus.PENDING);
      createdOrder.getPaymentDetailsEntity().setStatus(PaymentStatus.PENDING);

      Order savedOrder = orderRepository.save(createdOrder);
      orders.add(savedOrder);

      List<OrderItem> orderItems = new ArrayList<>();
      for (CartItem item : items) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(savedOrder);
        orderItem.setMrpPrice(item.getMrpPrice());
        orderItem.setProduct(item.getProduct());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setSize(item.getSize());
        orderItem.setUserId(item.getUserId());
        orderItem.setSellingPrice(item.getSellingPrice());

        savedOrder.getOrderItemList().add(orderItem);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        orderItems.add(savedOrderItem);
      }
    }
    return orders;
  }

  @Override
  public Order findOrderById(Long id) throws Exception {
    return orderRepository.findById(id).orElseThrow(() ->
        new Exception(("Order not found..")));
  }

  @Override
  public List<Order> usersOrderHistory(Long userId) {
    return orderRepository.findByUserId(userId);
  }

  @Override
  public List<Order> sellersOrder(Long sellerId) {
    return orderRepository.findBySellerId(sellerId);
  }

  @Override
  public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
    Order order = findOrderById(orderId);
    order.setOrderStatus(orderStatus);
    return orderRepository.save(order);

  }

  @Override
  public Order cancelOrder(Long orderId, User user) throws Exception {
    // 1) fetch + access check (same logic as before)
    Order order = findOrderById(orderId);
    if (user.getId().equals(order.getUser().getId())) {
      throw new Exception("You don't have access.");
    }

    // 2) mark as cancelled + persist
    order.setOrderStatus(OrderStatus.CANCELLED);
    Order saved = orderRepository.save(order);

    // 3) **moved from controller**: update seller report
    Seller seller = sellerService.getSellerById(saved.getSellerId());
    SellerReport report = sellerReportService.getSellerReport(seller);
    // increment cancelled count
    report.setCanceledOrders(report.getCanceledOrders() + 1);
    // add this order's total as refund
    report.setTotalRefunds(report.getTotalRefunds() + saved.getTotalSellingPrice());
    // persist the updated report
    sellerReportService.updateSellerReport(report);

    return saved;
  }

  @Override
  public OrderItem getOrderItemById(Long id) throws Exception {
    return orderItemRepository.findById(id).orElseThrow(() ->
        new Exception("Order item doesn't exist."));
  }
}
