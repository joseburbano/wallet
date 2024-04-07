package test.wallet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import test.wallet.common.exception.ErrorData;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private boolean successful;
    private ErrorData data;
    private String message;
    private int code;
}