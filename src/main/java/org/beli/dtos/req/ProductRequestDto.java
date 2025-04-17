package org.beli.dtos.req;

public record ProductRequestDto(String code, String productType, String formType, String phaseCode,
                                Long amount, Long transferFee, String color, String size,
                                Long price) {
}
