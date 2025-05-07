package com.springvalidation.demo.dto;

import com.springvalidation.demo.validation.groups.AdvanceInfo;
import com.springvalidation.demo.validation.groups.BasicInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    @NotBlank
    private String id;

    @NotBlank(groups = AdvanceInfo.class)
    private String name;

    @Positive
    private Long quantity;
}
