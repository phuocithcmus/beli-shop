package org.beli.services;

import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.req.UpdateRevenueRequestDto;
import org.beli.dtos.res.RevenueFeeResponseDto;
import org.beli.dtos.res.RevenueResponseDto;
import org.beli.entities.Product;
import org.beli.entities.Revenues;
import org.beli.repositories.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RevenueService extends BaseService<Revenues, String> {
    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private FeeService feeService;

    @Autowired
    private ProductService productService;


    public RevenueService(RevenueRepository revenueRepository) {
        super(revenueRepository);
    }

    public Revenues createNewRevenue(CreateRevenueRequestDto dto) {

        var product = productService.findById(dto.productId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        var revenues = mappingToCreateEntity(dto);
        var newRevenues = revenueRepository.save(revenues);

        if (newRevenues == null) {
            throw new RuntimeException("Failed to create revenue");
        }
        var remainingAmount = product.getRemainingAmount() - dto.amount();
        product.setRemainingAmount(remainingAmount);
        productService.update(product);

        System.out.println("Product remaining amount: " + product.getRemainingAmount());

        return newRevenues;
    }

    public Revenues mappingToCreateEntity(CreateRevenueRequestDto dto) {

        var feesOpt = feeService.findByFeePlatform(dto.channel());
        var product = productService.findById(dto.productId());


        if (feesOpt.isPresent()) {
            var fees = feesOpt.get().stream()
                    .map(fee -> new RevenueFeeResponseDto(
                            fee.getFeeType(),
                            fee.getFeePlatform(),
                            fee.getFeeAmount()
                    ))
                    .toArray(RevenueFeeResponseDto[]::new);

            var feeAmount = Arrays.stream(fees).mapToLong(RevenueFeeResponseDto::getPrice).sum();

            var revenue = dto.receivedAmount() - (product.getPrice() + product.getTransferFee()) * dto.amount() - feeAmount;

            Revenues revenues = new Revenues();
            revenues.setChannel(dto.channel());
            revenues.setPrice(dto.price());
            revenues.setSellPrice(dto.sellPrice());
            revenues.setProductId(dto.productId());
            revenues.setAmount(dto.amount());
            revenues.setFees(dto.fees());
            revenues.setCreatedAt(System.currentTimeMillis());
            revenues.setUpdatedAt(System.currentTimeMillis());
            revenues.setRevenue(revenue);
            revenues.setReceivedAmount(dto.receivedAmount());

            return revenues;
        } else {
            throw new RuntimeException("Phase not found");
        }
    }

    public RevenueResponseDto mappingToRevenueResponse(Revenues dto) {

        var feesOpt = feeService.findByFeePlatform(dto.getChannel());
        var product = productService.findById(dto.getProductId());

        if (product == null) {
            return null;
        }

        if (feesOpt.isPresent()) {
            var fees = feesOpt.get().stream()
                    .map(fee -> new RevenueFeeResponseDto(
                            fee.getFeeType(),
                            fee.getFeePlatform(),
                            fee.getFeeAmount()
                    ))
                    .toArray(RevenueFeeResponseDto[]::new);


            return new RevenueResponseDto(
                    dto.getId(),
                    dto.getChannel(),
                    dto.getPrice(),
                    dto.getSellPrice(),
                    dto.getRevenue(),
                    dto.getAmount(),
                    dto.getReceivedAmount(),
                    fees,
                    product.getId(),
                    product.getCode()
            );
        } else {
            throw new RuntimeException("Phase not found");
        }
    }

    public Revenues mappingToUpdateEntity(UpdateRevenueRequestDto dto) {
        var revenuesOpt = revenueRepository.findById(dto.id());
        if (revenuesOpt.isEmpty()) {
            throw new RuntimeException("Revenue not found");
        }
        var revenues = revenuesOpt.get();
        revenues.setChannel(dto.channel());
        revenues.setPrice(dto.price());
        revenues.setSellPrice(dto.sellPrice());
        revenues.setProductId(dto.productId());
        revenues.setAmount(dto.amount());
        revenues.setFees(dto.fees());
        revenues.setUpdatedAt(System.currentTimeMillis());

        return revenues;
    }

    public Revenues updateRevenue(UpdateRevenueRequestDto dto) {
        var revenues = mappingToUpdateEntity(dto);
        var updatedRevenues = revenueRepository.save(revenues);

        var revenuesOpt = revenueRepository.findById(dto.id());

        if (revenuesOpt.isEmpty()) {
            throw new RuntimeException("Revenue not found");
        }
        var revenuesEntity = revenuesOpt.get();

        var product = productService.findById(dto.productId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        if (updatedRevenues == null) {
            throw new RuntimeException("Failed to update revenue");
        }

        var currentAmount = revenuesEntity.getAmount(); // 3

        var remainingAmount = product.getRemainingAmount() + (currentAmount - dto.amount());
        product.setRemainingAmount(remainingAmount);
        productService.update(product);

        return updatedRevenues;
    }
}
