package com.springvalidation.demo.view;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ValidationErrorListView {

    private String message = "Some fields are not valid";

    private List<ValidationErrorView> violations = new ArrayList<>();
}
