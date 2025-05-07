package com.springvalidation.demo.controller;

import com.springvalidation.demo.dto.UserAccountDto1;
import com.springvalidation.demo.dto.UserAccountDto2;
import com.springvalidation.demo.validation.groups.AdvanceInfo;
import com.springvalidation.demo.validation.groups.BasicInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user", produces = {"application/json"})
@RestController
@Validated //For query/path params @Validated is used here
public class UserAccountController {
    @PostMapping("/add-all-info") // @Valid annotation is used to validate the method argument() and nested attributes
    public void addAllInfo(@Valid @RequestBody UserAccountDto1 userAccountDto2) {}

    @GetMapping("/{id}") // For query/path params use @Validated for controller
    public void getInfo(@NotNull @Size(min=4, max = 5) @PathVariable String id,
                        @NotNull @Size(min=2) @RequestParam(required = false) String active) {}
    // You should use required = false and add validation here for best Client Experience

    /*
    In my opinion only the above should be used in controller.
    The implementation below using @Validated does partial validations which is an antipattern and should be avoided.
    Instead, separate classes should be used.
    */
    @PostMapping("/add-basic-info")
    /*@Validated annotation is used here if we want granular validation
    only for fields that belong to a particular group. Here BasicInfo group is used.
    In UserAccountDto username and password have Constraints belonging to group BasicInfo.
    */
    public void addBasicInfo(@Validated(BasicInfo.class) @RequestBody UserAccountDto2 userAccountDto2) {}

    @PostMapping("/add-advanced-info")
    /*
    In UserAccountDto age, accountId and dateOfBirth have Constraints belonging to group AdvanceInfo.
    */
    public void addAdvancedInfo(@Validated(AdvanceInfo.class) @RequestBody UserAccountDto2 userAccountDto2) {}

    @PostMapping("/add-all-info-2")// Here only validation on userAccountDto2.returnedProductsCount works
    // because other validations constraints belong to specific groups.
    public void addAllInfo2(@Valid @RequestBody UserAccountDto2 userAccountDto2) {}
}
