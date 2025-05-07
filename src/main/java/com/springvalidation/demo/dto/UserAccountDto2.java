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
public class UserAccountDto2 {

    @NotNull(groups = BasicInfo.class)
    @Size(min = 4, max = 15, groups = BasicInfo.class)
    private String password;

    @NotBlank(groups = BasicInfo.class)
    private String username;

    @Min(value = 18, message = "Age should not be less than 18", groups = AdvanceInfo.class)
    private int age;

    private List<@Valid ProductDto> ownedProducts;

    private Map<String,@Valid ProductDto> ownedProductsMap;

    @Pattern(regexp = "^ACCOUNT-", groups = AdvanceInfo.class)
    private String accountId;

    @DateOfBirth(groups = AdvanceInfo.class)
    private String dateOfBirth;

    @NotNull
    @Min(1)
    private Integer returnedProductsCount;
}
