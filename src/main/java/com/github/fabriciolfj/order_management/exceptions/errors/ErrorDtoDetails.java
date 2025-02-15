package com.github.fabriciolfj.order_management.exceptions.errors;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDtoDetails {

    private String field;
    private String message;
}
