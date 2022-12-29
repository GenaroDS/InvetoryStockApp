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

    @Query("select p from Product p " +
            "where lower(p.name) like lower(concat('%', :searchTerm, '%')) " +
            "and USER_INFO = :userId")
    List<Product> search2(@Param("searchTerm") String searchTerm, @Param("userId") Long userId);
//
//    List<Product> findById(String id);
//    @Query("select c from Product c " +
//            "where lower(NAME) like lower(concat('%', :searchTerm, '%')) ")
//    List<Product> searchProduct(@Param("searchTerm") String searchTerm);

//    @Query("select c from Product c " +
//            "where lower(NAME) like lower(concat('%', :searchTerm, '%')) and lower(b) like lower(concat('%', :searchTerm, '%')) \"")
//    List<Product> searchProductAndFilter(@Param("searchTerm") String searchTerm, @Param("b") String b);


}