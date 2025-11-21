package com.kuza.kuzasokoni.domain.product.repositories;

import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
    SELECT p.id AS id,
           p.productName AS productName,
           p.shortName AS shortName,
           p.interestMin AS interestMin,
           p.interestMax AS interestMax,
           p.interestMethod AS interestMethod,
           p.repaidEvery AS repaidEvery,
           p.currency AS currency,
           p.status AS status
    FROM Product p
""")
    Page<ProductView> findAllProductViews(Pageable pageable);

    @Query("""
    SELECT p.id AS id,
           p.productName AS productName,
           p.shortName AS shortName,
           p.interestMin AS interestMin,
           p.interestMax AS interestMax,
           p.interestMethod AS interestMethod,
           p.repaidEvery AS repaidEvery,
           p.currency AS currency,
           p.status AS status
    FROM Product p
    WHERE p.id = :id
""")
    Optional<ProductView> findProductViewById(@Param("id") Long id);

    @Query("SELECT i FROM Images i WHERE i.id = :id")
    Optional<ImageView> findProjectedById(Long id);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.charges " +
            "LEFT JOIN FETCH p.tenures " +
            "WHERE p.id = :id")
    Product findProductWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.charges " +
            "LEFT JOIN FETCH p.tenures")
    Page<Product> findAllProductsWithDetails(Pageable pageable);

}
