package com.pp.grup.Repository;

import com.pp.grup.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    void deleteBySearchName(String searchName);

    List<Product> findBySearchName(String searchName);
}
