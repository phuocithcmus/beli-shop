package org.beli.dtos.req;

public record UpdateRevenueRequestDto(String id,
                                      String channel,
                                      Long price,
                                      Long sellPrice,
                                      Long receivedAmount,
                                      String productId,
                                      Long amount,
                                      Long packageFee) {
}
