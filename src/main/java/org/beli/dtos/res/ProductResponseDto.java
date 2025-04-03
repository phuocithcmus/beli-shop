package org.beli.dtos.res;

import lombok.Getter;
import lombok.Setter;

public class ProductResponseDto {


    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String phaseCode;

    @Getter
    @Setter
    private String phaseName;

    @Getter
    @Setter
    private String productType;

    @Getter
    @Setter
    private String formType;

    @Getter
    @Setter
    private String size;

    @Getter
    @Setter
    private String color;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private Long remainingAmount;

    @Getter
    @Setter
    private Long price;

    @Getter
    @Setter
    private Long transferFee;

    public ProductResponseDto(String id, String code, String phaseCode, String phaseName,
                              String productType, String formType, String size, String color,
                              Long amount, Long remainingAmount, Long price, Long transferFee) {
        this.id = id;
        this.code = code;
        this.phaseCode = phaseCode;
        this.phaseName = phaseName;
        this.productType = productType;
        this.formType = formType;
        this.size = size;
        this.color = color;
        this.amount = amount;
        this.remainingAmount = remainingAmount;
        this.price = price;
        this.transferFee = transferFee;
    }
}
