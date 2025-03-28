package org.beli.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    public ResponseDto(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    @Setter
    private HttpStatus code;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private T data;
}
