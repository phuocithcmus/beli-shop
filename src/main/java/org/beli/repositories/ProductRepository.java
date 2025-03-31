package org.beli.repositories;

import org.beli.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // if you need to find a product by its name, you can uncomment the following method
}
