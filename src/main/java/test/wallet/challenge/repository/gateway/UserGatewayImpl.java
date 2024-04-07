package test.wallet.challenge.repository.gateway;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import test.wallet.challenge.domain.gateways.AccountRepository;
import test.wallet.challenge.domain.gateways.UserRepository;
import test.wallet.challenge.domain.model.AccountDTO;
import test.wallet.challenge.domain.model.UserDTO;
import test.wallet.challenge.repository.SqlServerUserRepository;
import test.wallet.challenge.repository.entities.User;
import test.wallet.common.enums.ErrorCode;
import test.wallet.common.exception.InfrastructureException;

import java.util.Locale;

@Repository
@AllArgsConstructor
public class UserGatewayImpl implements UserRepository {

    private final ModelMapper modelMapper;

    private final MessageSource messageSource;

    private static final String RESOURCE_NAME = "UserGateway";

    private final SqlServerUserRepository userRepository;

    private final AccountRepository accountRepository;

    @Override
    public Mono<UserDTO> save(UserDTO userDTO) {
        return Mono.just(userRepository.findByUserId(userDTO.getUserId()))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(entity -> {
                    if (entity != null) {
                        return Mono.error(new InfrastructureException(
                                messageSource.getMessage("comun.recurso.existe",
                                        new Object[]{RESOURCE_NAME}, Locale.getDefault()),
                                ErrorCode.FOUND));
                    } else {
                        return Mono.just(userRepository.save(modelMapper.map(userDTO, User.class)))
                                .flatMap(savedEntity ->
                                        accountRepository.save(AccountDTO.builder()
                                                        .user(savedEntity)
                                                        .active(true)
                                                        .amount(0.0)
                                                        .build())
                                                .thenReturn(modelMapper.map(savedEntity, UserDTO.class))
                                );
                    }
                });
    }

    @Override
    public Mono<UserDTO> findByUserId(String userId) {
        return Mono.justOrEmpty(userRepository.findByUserId(userId))
                .switchIfEmpty(Mono.error(new InfrastructureException(messageSource.getMessage("comun.recurso.no.encontrado",
                        new Object[]{RESOURCE_NAME}, Locale.getDefault()), ErrorCode.NOT_FOUND)))
                .map(entity -> modelMapper.map(entity, UserDTO.class));
    }
}
