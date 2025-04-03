package org.beli.services;

import org.beli.dtos.req.ProductRequestDto;
import org.beli.entities.Product;
import org.beli.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, String> {
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    public Product mappingToCreateEntity(ProductRequestDto dto) {
        var product = new Product();
        product.setCode(dto.code());
        product.setProductType(dto.productType());
        product.setFormType(dto.formType());
        product.setPhaseCode(dto.phaseCode());
        product.setEntryDate(dto.entryDate());
        product.setAmount(dto.amount());
        product.setTransferFee(dto.transferFee());
        product.setRemainingAmount(dto.remainingAmount());
        product.setCreatedAt(System.currentTimeMillis());
        product.setUpdatedAt(System.currentTimeMillis());
        return product;
    }
}
