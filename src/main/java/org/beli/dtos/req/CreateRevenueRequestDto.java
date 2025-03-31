package org.beli.dtos.req;

public record CreateRevenueRequestDto(String channel,
                                      Long price,
                                      Long sellPrice,
                                      Long revenue,
                                      String productId,
                                      Long amount,
                                      String fees) {
}
