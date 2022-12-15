package com.example.demo.backend.service;

import com.example.demo.backend.entity.Product;
import com.example.demo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductService implements CrudListener<Product> {

    private final ProductRepository repository;

    @Override
    public Collection<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product add(Product product) {
        return repository.save(product);
    }

    @Override
    public Product update(Product product) {
        return repository.save(product);
    }

    @Override
    public void delete(Product product) {
        repository.delete(product);
    }
}
