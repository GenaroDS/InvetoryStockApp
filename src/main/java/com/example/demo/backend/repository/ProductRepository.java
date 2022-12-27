package com.example.demo.backend.repository;

import com.example.demo.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select c from Product c " +
            "where USER_INFO = :searchTerm ")
    List<Product> search(@Param("searchTerm") Long searchTerm);
//
//    List<Product> findById(String id);
    @Query("select c from Product c " +
            "where lower(NAME) like lower(concat('%', :searchTerm, '%')) ")
    List<Product> searchProduct(@Param("searchTerm") String searchTerm);
}