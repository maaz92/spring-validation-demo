package com.springvalidation.demo.controller.advice;

import com.springvalidation.demo.view.ValidationErrorListView;
import com.springvalidation.demo.view.ValidationErrorView;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorListView onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorListView error = new ValidationErrorListView();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new ValidationErrorView(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorListView onConstraintViolationException(ConstraintViolationException e) {
        ValidationErrorListView error = new ValidationErrorListView();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            error.getViolations().add(new ValidationErrorView(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        }
        return error;
    }
}
