package org.beli.handler;

import org.beli.dtos.req.ProductRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.res.ProductResponseDto;
import org.beli.entities.Product;
import org.beli.services.PhaseService;
import org.beli.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @GetMapping(value = "/download_excel")
    public HttpEntity<ByteArrayResource> createExcelWithTaskConfigurations() throws IOException {
        try {
            ByteArrayOutputStream stream = productService.exportToExcel("products");
            ByteArrayResource resource = new ByteArrayResource(stream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xlsx");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(stream.size())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
