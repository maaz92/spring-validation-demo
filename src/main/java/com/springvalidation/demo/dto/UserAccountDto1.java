package com.springvalidation.demo.dto;

import com.springvalidation.demo.validation.constraints.DateOfBirth;
import com.springvalidation.demo.validation.groups.AdvanceInfo;
import com.springvalidation.demo.validation.groups.BasicInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserAccountDto1 {

    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    @NotBlank
    private String username;

    @Min(value = 18, message = "Age should not be less than 18")
    private int age;

    private List<@Valid ProductDto> ownedProducts;

    private Map<String,@Valid ProductDto> ownedProductsMap;

    @Pattern(regexp = "^ACCOUNT-")
    private String accountId;

    @DateOfBirth
    private String dateOfBirth;

    @NotNull
    @Min(1)
    private Integer returnedProductsCount;
}
