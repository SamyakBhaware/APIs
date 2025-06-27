package com.API5.Task.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
}
