package test.wallet.challenge.repository.gateway;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import test.wallet.challenge.domain.gateways.AccountRepository;
import test.wallet.challenge.domain.model.AccountDTO;
import test.wallet.challenge.repository.SqlServerAccountRepository;
import test.wallet.challenge.repository.entities.Account;
import test.wallet.common.enums.ErrorCode;
import test.wallet.common.exception.InfrastructureException;

@Repository
@AllArgsConstructor
public class AccountGatewayImpl implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountGatewayImpl.class);
    private final SqlServerAccountRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public Mono<Void> save(AccountDTO accountDTO) {
        log.info("Save from accountGatewayImpl {} ", accountDTO);
        return Mono.fromSupplier(() -> repository.save(modelMapper.map(accountDTO, Account.class)))
                .flatMap(savedEntity -> Mono.empty());
    }

    @Override
    public Mono<Double> updateAmount(Double amount, Integer userId) {
        log.info("UpdateAmount from accountGatewayImpl {} {}", amount, userId);
        return Mono.justOrEmpty(repository.findByUserId(userId))
                .switchIfEmpty(Mono.error(new InfrastructureException(ErrorCode.NOT_FOUND.getMessage(), ErrorCode.NOT_FOUND)))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(accountEntity -> {
                    accountEntity.setAmount(accountEntity.getAmount() + amount);
                    Account updatedEntity = repository.save(accountEntity);
                    return Mono.just(updatedEntity.getAmount());
                });
    }

    @Override
    public Mono<AccountDTO> findByUserId(Integer userId) {
        log.info("FindByUserId from accountGatewayImpl {} ", userId);
        return Mono.justOrEmpty(repository.findByUserId(userId))
                .switchIfEmpty(Mono.error(new InfrastructureException(ErrorCode.NOT_FOUND.getMessage(), ErrorCode.NOT_FOUND)))
                .map(entity -> modelMapper.map(entity, AccountDTO.class));
    }
}
