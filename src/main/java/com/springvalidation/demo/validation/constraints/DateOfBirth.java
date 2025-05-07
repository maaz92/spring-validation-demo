package com.springvalidation.demo.validation.constraints;

import com.springvalidation.demo.validation.constraints.validator.DateOfBirthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirth {
    String message() default "Invalid Date Of Birth. Expected MM/dd/yyyy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
