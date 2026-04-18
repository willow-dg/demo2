package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "profiles")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)//自动排序
    private Long id;
    @Column(name = "bio")
    private String bio;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    @ToString.Exclude
    private User user;
}
