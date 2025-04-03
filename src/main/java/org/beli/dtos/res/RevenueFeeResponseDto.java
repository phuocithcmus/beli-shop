package org.beli.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RevenueFeeResponseDto {

    @Getter
    private String type;

    @Getter
    private String channel;

    @Getter
    private Long price;


}
