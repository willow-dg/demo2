package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
@ToString
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

//    @Override
//    public String toString() {
//        return getClass().getSimpleName() + "(" +
//                "id = " + id + ", " +
//                "name = " + name + ", " +
//                "email = " + email + ")";
//    }
}
