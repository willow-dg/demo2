package com.example.demo.repositories;

import com.example.demo.dtos.UserSummary;
import com.example.demo.entities.Profile;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"tags", "addresses"})
    Optional<User> findByEmail(String email);

    @Query("select u from User u")
    @EntityGraph(attributePaths = "addresses")
    List<User> findAllwithAddresses();

//    @EntityGraph(attributePaths = "user")
    @Query("select u.id id,u.email email from User u where u.profile.loyaltyPoints > :loyaltyPoints order by u.email")
    List<UserSummary> findByLoyaltyPointsGreaterThanOrderByUserEmail(@Param("loyaltyPoints") int loyaltyPoints);

}