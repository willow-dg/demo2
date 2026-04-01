package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @Column(name = "id")
    private Long tag;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<User> users = new HashSet<>();
}
