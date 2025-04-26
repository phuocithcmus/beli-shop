package org.beli.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class RemainningProductResponseDto {

    @Getter
    @Setter
    String productCode;

    @Getter
    @Setter
    Long remainingAmount;


}
