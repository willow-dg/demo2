package com.example.demo.repositories;

import com.example.demo.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.classfile.Opcode;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @EntityGraph(attributePaths = "items.product")
    @Query("select c from Cart c where c.id = :cartId")
    Optional<Cart> getCartWithItems(@Param("cartId") UUID cartId);
}