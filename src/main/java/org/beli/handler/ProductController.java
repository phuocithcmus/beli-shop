package org.beli.handler;

import org.beli.dtos.req.ProductRequestDto;
import org.beli.dtos.res.ProductResponseDto;
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

    @Autowired
    private PhaseService phaseService;

    @PostMapping
    public Product createPhase(@RequestBody ProductRequestDto product) {
        return productService.save(productService.mappingToCreateEntity(product));
    }

    @GetMapping("")
    public List<ProductResponseDto> getAll() {
        var products = productService.findAll();
        if (products.isEmpty()) {
            return List.of();
        }
        return products.stream().map(e -> {
            return productService.mappingToProductResponse(e);
        }).toList();
    }


    @GetMapping("/{phaseCode}")
    public List<ProductResponseDto> getAllByPhaseCode(@PathVariable("phaseCode") String phaseCode) {
        var products = productService.findAllProductByPhaseCode(phaseCode);
        if (products.isEmpty()) {
            return List.of();
        }
        return products;
    }
}
