package org.beli.dtos.req;

public record CreateFeeRequestDto(String feePlatform, String feeType, Long feeAmount) {
}
