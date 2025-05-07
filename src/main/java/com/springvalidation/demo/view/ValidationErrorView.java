package com.springvalidation.demo.view;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ValidationErrorView {

    private String fieldName;

    private String message;
}
