package test.wallet.challenge.domain.services;

import reactor.core.publisher.Mono;
import test.wallet.challenge.domain.model.BalanceDTO;
import test.wallet.challenge.domain.model.RechargeWalletDTO;
import test.wallet.challenge.domain.model.UserDTO;

public interface ChallengeService {

    Mono<BalanceDTO> balanceWallet(String userId, String phone);

    Mono<UserDTO> registerUser(UserDTO registerUserDTO);

    Mono<BalanceDTO> rechargeWallet(RechargeWalletDTO rechargeWalletDTO);
}
