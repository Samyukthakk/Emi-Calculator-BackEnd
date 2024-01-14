package com.si.pnrcalc.api.exception;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RestErrorResponse {

    private String errorCode;

    private List<String> messages;

    public RestErrorResponse(String errorCode, List<String> messages) {
        this.errorCode = errorCode;
        this.messages = messages;
    }
}
