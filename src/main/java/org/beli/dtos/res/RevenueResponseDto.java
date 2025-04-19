package org.beli.dtos.res;

import lombok.Getter;
import lombok.Setter;

public class RevenueResponseDto {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String channel;

    @Getter
    @Setter
    private Long price;

    @Getter
    @Setter
    private Long sellPrice;

    @Getter
    @Setter
    private Long revenue;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private Long receivedAmount;

    @Getter
    @Setter
    private RevenueFeeResponseDto[] fees;

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private String productCode;

    @Getter
    @Setter
    private Long packageFee;

    public RevenueResponseDto(String id, String channel, Long price, Long sellPrice, Long revenue,
                              Long amount, Long receivedAmount, RevenueFeeResponseDto[] fees, String productId, String productCode, Long packageFee) {
        this.id = id;
        this.channel = channel;
        this.price = price;
        this.sellPrice = sellPrice;
        this.revenue = revenue;
        this.amount = amount;
        this.receivedAmount = receivedAmount;
        this.fees = fees;
        this.productId = productId;
        this.productCode = productCode;
        this.packageFee = packageFee;
    }
}
