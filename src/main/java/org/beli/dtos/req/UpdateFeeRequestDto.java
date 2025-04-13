package org.beli.dtos.req;

public record UpdateFeeRequestDto(String id, String feePlatform, String feeType, Long feeAmount) {
}
