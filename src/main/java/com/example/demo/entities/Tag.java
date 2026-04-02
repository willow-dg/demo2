package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }
}
