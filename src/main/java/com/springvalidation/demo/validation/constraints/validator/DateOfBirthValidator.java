package com.springvalidation.demo.validation.constraints.validator;

import com.springvalidation.demo.validation.constraints.DateOfBirth;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, String> {

    @Override
    public boolean isValid(String dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(Boolean.FALSE);
        try
        {
            simpleDateFormat.parse(dateOfBirth);
            return Boolean.TRUE;
        }
        /* Date format is invalid */
        catch (ParseException e)
        {
            log.error("Some error message");
            return Boolean.FALSE;
        }
    }
}
