package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.PERSIST,orphanRemoval = true)
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    public Category(byte b) {
        this.id = b;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
    }
    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setCategory(null);
    }

}