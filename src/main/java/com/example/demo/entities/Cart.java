package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "Carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate localDate;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE,orphanRemoval = true)
    private Set<CartItem> items = new LinkedHashSet<>();

    //获取总价
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //处理数据
    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

    }

    //添加商品
    public CartItem addItem(Product product) {
        //判断是有符合id的产品
        var cartItem = getItem(product.getId());
        //不为空，数量加1；为空，创建购物车商品
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(this);
            cartItem.setQuantity(1);
            items.add(cartItem);
        }
        return cartItem;
    }

    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
