package com.example.demo.repositories;

import com.example.demo.dtos.ProductSummary;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,ProductCriteriaRepository, JpaSpecificationExecutor<Product> {
    List<Product> findByName(String name);

    List<Product> findByNameLike(String name);

    List<Product> findByNameNotLike(String name);

    List<Product> findByNameContaining(String name);

    List<Product> findByNameStartingWith(String name);

    List<Product> findByNameEndingWith(String name);

    //    忽略大小写
    List<Product> findByNameEndingWithIgnoreCase(String name);

    List<Product> findByIdOrderById(Long id);

    //Limit(Top or First)
    List<Product> findFirst6ByNameLikeOrderByPrice(String name);

    List<Product> findTop6ByNameLikeOrderByPriceDesc(String name);

    //between
    List<Product> findProductBetweenOrderById(Long aLong, Long bLong);

    //查询价格区间内的商品，并按Name排序
    @Query("select p from Product p where p.price between :min and :max order by p.name")
    List<Product> findByPriceBetweenOrderByName(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    //SQL or JPQL
    @Procedure("findProductsByPrice")
    List<Product> findProducts(BigDecimal min, BigDecimal max);

    //计算给定范围内的所有商品的数量
    @Query("select count(*) from Product p where p.price between :min and :max")
    Long countProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    //修改类别的数据
    @Modifying
    @Query("update Product p set p.price = :price where p.category.id=:categoryId")
    void updatePriceByCategory(@Param("categoryId") byte categoryId, @Param("price") BigDecimal price);

    @Query("select p.id id from Product p where p.category = :category")
    List<ProductSummary> findByCategory(@Param("category") Category category);

}