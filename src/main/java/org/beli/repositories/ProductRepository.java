package org.beli.repositories;

import org.beli.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // if you need to find a product by its name, you can uncomment the following method

    Optional<List<Product>> findByPhaseCode(String phaseCode);
}
