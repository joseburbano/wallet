package test.wallet.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import test.wallet.common.exception.ErrorResponse;
import test.wallet.common.exception.InfrastructureException;
import test.wallet.common.exception.ErrorData;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(InfrastructureException ex) {
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getErrorCode().getCode());
        ErrorResponse errorResponse = new ErrorResponse(false, new ErrorData(ex.getMessage()), httpStatus.getReasonPhrase(), ex.getErrorCode().getCode());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    // Otros manejadores de excepciones si es necesario

}
