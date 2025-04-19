package org.beli.handler;

import org.beli.dtos.req.ProductRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.res.ProductResponseDto;
import org.beli.entities.Product;
import org.beli.services.PhaseService;
import org.beli.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @PatchMapping
    public Product updatePhase(@RequestBody UpdateProductRequestDto product) {
        return productService.save(productService.mappingToUpdateEntity(product));
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable("id") String id) {
        productService.deleteById(id);
        return true;
    }

    @GetMapping("")
    public List<ProductResponseDto> getAll() {
        var products = productService.findAllSortBy("createdAt");
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
