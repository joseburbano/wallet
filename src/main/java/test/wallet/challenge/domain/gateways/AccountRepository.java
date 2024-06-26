package test.wallet.challenge.domain.gateways;

import reactor.core.publisher.Mono;
import test.wallet.challenge.domain.model.AccountDTO;

public interface AccountRepository {

    Mono<Void> save(AccountDTO accountDTO);

    Mono<Double> updateAmount(Double amount, Integer userId);

    Mono<AccountDTO> findByUserId(Integer userId);
}
