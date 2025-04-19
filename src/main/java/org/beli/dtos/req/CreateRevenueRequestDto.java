package org.beli.dtos.req;

public record CreateRevenueRequestDto(String channel,
                                      Long price,
                                      Long sellPrice,
                                      Long receivedAmount,
                                      String productId,
                                      Long amount,
                                      Long packageFee) {
}
