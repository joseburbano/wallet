package test.wallet.common.controller;

import test.wallet.common.dto.ResponseDTO;
import test.wallet.common.enums.ErrorCode;

/**
 * Abstract class that implements general controller functionalities.
 */
public abstract class AbstractRestController {

    protected ResponseDTO buildSuccessResponseDTO(Object result) {
        return ResponseDTO.builder()
                .successful(true)
                .data(result)
                .message(ErrorCode.OK.getMessage())
                .code(ErrorCode.OK.getCode())
                .build();
    }
}
