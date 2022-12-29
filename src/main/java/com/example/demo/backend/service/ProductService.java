package com.example.demo.backend.service;

import com.example.demo.backend.entity.Product;
import com.example.demo.backend.entity.UserInfo;
import com.example.demo.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service

public class ProductService {

    private final ProductRepository repository;


    public ProductService(ProductRepository repository) {
        this.repository = repository;

    }
//    public List<Product> findAllProducts(String stringFilter) {
//        if (stringFilter == null || stringFilter.isEmpty()) {
//            return repository.findAll();
//        } else {
//            return repository.searchProduct(stringFilter);
//        }
//    }


//    public List<Product> findAllProducts(String currentUser){
//        if (currentUser == null || currentUser.isEmpty()){
//            return repository.findAll();
//        } else {
//            return repository.search(currentUser);
//        }
//    }

//    public List<Product> searchByFilter(String filterText) {
//        return this.repository.searchProduct(filterText);
//
//    }
    public List<Product> findAll(){
        return this.repository.findAll();
    }

    public List<Product> find(Long id){
        return this.repository.search(id);
    }

    public List<Product>find2(Long id, String filterText){
        return this.repository.search2(filterText ,id);
    }
    public Product add(Product product) {
        return repository.save(product);
    }

    public Product update(Product product) {
        return repository.save(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public void addUserInfo(UserInfo userInfo){
    }


}
