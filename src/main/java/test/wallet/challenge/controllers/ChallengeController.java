package test.wallet.challenge.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.wallet.challenge.domain.model.RechargeWalletDTO;
import test.wallet.challenge.domain.model.UserDTO;
import test.wallet.challenge.domain.services.ChallengeService;
import test.wallet.common.controller.AbstractRestController;
import test.wallet.common.dto.ResponseDTO;


@RestController
@RequestMapping(value = "/v1/challenge")
@AllArgsConstructor
public class ChallengeController extends AbstractRestController {
    private static final Logger log = LoggerFactory.getLogger(ChallengeController.class);
    private final ChallengeService challengeService;

    @GetMapping(value = "/ping")
    public ResponseEntity<Object> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @GetMapping(value = "/wallet/balance", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> balanceWallet(@RequestParam("userId") String userId,
                                                     @RequestParam("phone") String phone) {
        ResponseDTO responseDTO = buildSuccessResponseDTO(challengeService.balanceWallet(userId, phone).block());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO registerUserDTO) {

        ResponseDTO responseDTO = buildSuccessResponseDTO(challengeService.registerUser(registerUserDTO).block());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/wallet/recharge", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ResponseDTO> rechargeWallet(@Valid @RequestBody RechargeWalletDTO rechargeWalletDTO) {

        ResponseDTO responseDTO = buildSuccessResponseDTO(challengeService.rechargeWallet(rechargeWalletDTO).block());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}


