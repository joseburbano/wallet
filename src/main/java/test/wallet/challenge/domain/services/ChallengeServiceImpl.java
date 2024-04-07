package test.wallet.challenge.domain.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import test.wallet.challenge.domain.gateways.AccountRepository;
import test.wallet.challenge.domain.gateways.UserRepository;
import test.wallet.challenge.domain.model.BalanceDTO;
import test.wallet.challenge.domain.model.RechargeWalletDTO;
import test.wallet.challenge.domain.model.UserDTO;
import test.wallet.common.enums.ErrorCode;
import test.wallet.common.exception.InfrastructureException;

import java.util.Locale;
import java.util.Objects;


@Service
@AllArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private static final String RESOURCE_NAME = "ChallengeService";
    @NonNull
    private final MessageSource messageSource;

    @Override
    public Mono<BalanceDTO> balanceWallet(String userId, String phone) {
        log.info("balanceWallet {} ", userId + " " + phone);
        return findByUserId(userId, phone)
                .flatMap(userDTO -> accountRepository.findByUserId(userDTO.getUserId())
                        .map(accountDTO -> BalanceDTO.builder()
                                .fullName(userDTO.getFullName())
                                .userId(userDTO.getUserId())
                                .message("Su saldo")
                                .amount(accountDTO.getAmount())
                                .build())
                );
    }

    @Override
    public Mono<UserDTO> registerUser(UserDTO userDTO) {
        log.info("registerUser {} ", userDTO);
        return userRepository.save(userDTO);
    }

    @Override
    public Mono<BalanceDTO> rechargeWallet(RechargeWalletDTO rechargeWalletDTO) {
        log.info("rechargeWallet {} ", rechargeWalletDTO);
        return findByUserId(rechargeWalletDTO.getUserId(), rechargeWalletDTO.getPhone())
                .flatMap(userDTO ->
                        accountRepository.updateAmount(rechargeWalletDTO.getAmount(), rechargeWalletDTO.getUserId())
                                .flatMap(amount ->
                                        Mono.just(BalanceDTO.builder()
                                                .fullName(userDTO.getFullName())
                                                .userId(userDTO.getUserId())
                                                .message("Su nuevo saldo")
                                                .amount(amount)
                                                .build())
                                )
                );
    }

    private Mono<UserDTO> findByUserId(String userId, String phone) {
        log.info("findByUserId {} ", userId + " " + phone);
        return userRepository.findByUserId(userId)
                .flatMap(userDTO -> {
                    if (!Objects.equals(userDTO.getPhone(), phone)) {
                        return Mono.error(new InfrastructureException(messageSource.getMessage("common.resource.not.match",
                                new Object[]{RESOURCE_NAME}, Locale.getDefault()), ErrorCode.BAD_REQUEST));
                    }
                    return Mono.just(userDTO);
                });
    }
}
