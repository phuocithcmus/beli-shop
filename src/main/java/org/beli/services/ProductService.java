package org.beli.services;

import org.beli.dtos.req.ProductRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.res.ProductResponseDto;
import org.beli.entities.Product;
import org.beli.repositories.FeeRepository;
import org.beli.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends BaseService<Product, String> {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhaseService phaseService;

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    public Product mappingToCreateEntity(ProductRequestDto dto) {
        var product = new Product();
        product.setCode(dto.code());
        product.setProductType(dto.productType());
        product.setFormType(dto.formType());
        product.setPhaseCode(dto.phaseCode());
//        product.setEntryDate(dto.entryDate());
        product.setAmount(dto.amount());
        product.setTransferFee(dto.transferFee());
        product.setRemainingAmount(dto.remainingAmount());
        product.setCreatedAt(System.currentTimeMillis());
        product.setUpdatedAt(System.currentTimeMillis());
        product.setSize(dto.size());
        product.setColor(dto.color());
        product.setPrice(dto.price());
        return product;
    }


    public Product mappingToUpdateEntity(UpdateProductRequestDto dto) {
        var productOpt = productRepository.findById(dto.id());
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        var product = productOpt.get();
        product.setCode(dto.code());
        product.setProductType(dto.productType());
        product.setFormType(dto.formType());
        product.setPhaseCode(dto.phaseCode());
        product.setAmount(dto.amount());
        product.setTransferFee(dto.transferFee());
        product.setRemainingAmount(dto.remainingAmount());
        product.setCreatedAt(System.currentTimeMillis());
        product.setUpdatedAt(System.currentTimeMillis());
        product.setSize(dto.size());
        product.setColor(dto.color());
        product.setPrice(dto.price());
        return product;
    }


    public ProductResponseDto mappingToProductResponse(Product dto) {
        var phaseCode = dto.getPhaseCode();

        var phaseCodeOpt = phaseService.findByPhaseCode(phaseCode);
        if (phaseCodeOpt.isPresent()) {
            var phase = phaseCodeOpt.get();
            return new ProductResponseDto(
                    dto.getId(),
                    dto.getCode(),
                    phase.getPhaseCode(),
                    phase.getPhaseName(),
                    dto.getProductType(),
                    dto.getFormType(),
                    dto.getSize(),
                    dto.getColor(),
                    dto.getAmount(),
                    dto.getRemainingAmount(),
                    dto.getPrice(),
                    dto.getTransferFee()

            );
        } else {
            throw new RuntimeException("Phase not found");
        }
    }

    public List<ProductResponseDto> findAllProductByPhaseCode(String phaseCode) {
        var productOpt = productRepository.findByPhaseCode(phaseCode);

        if (productOpt.isPresent()) {
            return productOpt.get().stream()
                    .map(this::mappingToProductResponse)
                    .toList();
        } else {
            throw new RuntimeException("Product not found");
        }
    }
}
