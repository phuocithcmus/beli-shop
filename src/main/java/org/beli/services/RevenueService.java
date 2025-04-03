package org.beli.services;

import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.dtos.res.RevenueFeeResponseDto;
import org.beli.dtos.res.RevenueResponseDto;
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

            var revenue = dto.receivedAmount() - product.getPrice() - product.getTransferFee() - feeAmount;

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

        if (feesOpt.isPresent()) {
            var fees = feesOpt.get().stream()
                    .map(fee -> new RevenueFeeResponseDto(
                            fee.getFeeType(),
                            fee.getFeePlatform(),
                            fee.getFeeAmount()
                    ))
                    .toArray(RevenueFeeResponseDto[]::new);

            var feeAmount = Arrays.stream(fees).mapToLong(RevenueFeeResponseDto::getPrice).sum();

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
}
