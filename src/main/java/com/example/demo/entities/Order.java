package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "demo2")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrdersStatus status;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<OrderItem> items = new LinkedHashSet<>();

    public static Order fromCart(Cart cart,User customer) {
        var order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrdersStatus.PAYING);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem(order,item.getQuantity(),item.getProduct());
            order.items.add(orderItem);
        });
        return order;
    }

    public boolean isPlaceBy(User customer) {
        return this.customer.equals(customer);//通过注解实现
    }


}