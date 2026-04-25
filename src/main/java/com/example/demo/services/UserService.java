package com.example.demo.services;

import com.example.demo.entities.Address;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.ProfileRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.specifications.ProductSpec;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void showEntityStates() {
        var user = User.builder()
                .name("name")
                .password("password")
                .email("email")
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void showEntityStrategy() {
        var profile = profileRepository.findById(4L).orElseThrow();
        System.out.println(profile.getUser().getName());
    }

    public void persistRelated() {
        var user = User.builder()
                .name("name")
                .password("password")
                .email("email")
                .build();
        var address = Address.builder()
                .street("street")
                .city("city")
                .state("state")
                .zip("zip")
                .build();
        user.addAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void removeRelated() {
        var user = userRepository.findById(13L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void fetchProducts() {
        var product = new Product();
        product.setName("product");
        var matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("id","description","category","price")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        var example= Example.of(product,matcher);
        var products = productRepository.findAll(example);
        products.forEach(System.out::println);
    }

    public void fetchSortedProducts() {
        var sort = Sort.by("name","price").descending();
        productRepository.findAll(sort).forEach(System.out::println);
    }

    //分页查询
    public void fetchPaginatedProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageRequest);

        var products = productPage.getContent();
        products.forEach(System.out::println);

        var totalElements = productPage.getTotalElements();
        var totalPages = productPage.getTotalPages();
        System.out.println("Total elements: " + totalElements);
        System.out.println("Total pages: " + totalPages);
    }


    public void findProductsByCriteria() {
        var products = productRepository.findProductsByCriteria("product", BigDecimal.valueOf(1), BigDecimal.valueOf(100));
        products.forEach(System.out::println);
    }

    public void fetchProductsBySpecifications(String name,BigDecimal minPrice,BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }
        productRepository.findAll(spec).forEach(System.out::println);
    }

    @Transactional
    public void fetchProductsByCategory() {
        var products = productRepository.findByCategory(new Category((byte) 1));
        products.forEach(p-> System.out.println(p.getId()));
    }

    @Transactional
    public void fetchUsers() {
        var users = userRepository.findAllwithAddresses();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }

    @Transactional
    public void printLoyalProfile() {
        var users = userRepository.findByLoyaltyPointsGreaterThanOrderByUserEmail(3);
        users.forEach(p-> System.out.println(p.getId() +":"+ p.getEmail()));
    }
}
