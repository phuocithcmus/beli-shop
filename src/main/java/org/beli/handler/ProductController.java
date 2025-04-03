package org.beli.handler;

import org.beli.dtos.req.PhaseRequestDto;
import org.beli.dtos.req.ProductRequestDto;
import org.beli.entities.Phase;
import org.beli.entities.Product;
import org.beli.services.PhaseService;
import org.beli.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createPhase(@RequestBody ProductRequestDto product) {
        return productService.save(productService.mappingToCreateEntity(product));
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }
}
