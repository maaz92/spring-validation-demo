package com.springvalidation.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @Positive
    private Long quantity;
}
