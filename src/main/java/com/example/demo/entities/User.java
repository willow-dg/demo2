package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动排序
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Address> addresses = new ArrayList<>();
    @ManyToMany
    @Builder.Default
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();
    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Profile profile;
    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns =  @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")

    )
    private List<Product> products = new ArrayList<>();

    public void addAddress(Address address) {
        this.addresses.add(address);
        address.setUser(this);
    }
    public void removeAddress(Address address) {
        this.addresses.remove(address);
        address.setUser(null);
    }
}
