package test.wallet.challenge.repository.gateway;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import test.wallet.challenge.domain.gateways.AccountRepository;
import test.wallet.challenge.domain.gateways.UserRepository;
import test.wallet.challenge.domain.model.AccountDTO;
import test.wallet.challenge.domain.model.UserDTO;
import test.wallet.challenge.repository.SqlServerUserRepository;
import test.wallet.challenge.repository.entities.User;
import test.wallet.common.enums.ErrorCode;
import test.wallet.common.exception.InfrastructureException;

@Repository
@AllArgsConstructor
public class UserGatewayImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserGatewayImpl.class);
    private final ModelMapper modelMapper;

    private final SqlServerUserRepository userRepository;

    private final AccountRepository accountRepository;

    @Override
    public Mono<UserDTO> save(UserDTO userDTO) {
        log.info("Save from userGatewayImpl {} ", userDTO);
        return Mono.defer(() -> {
            User existingUser = userRepository.findByUserId(userDTO.getUserId());
            if (existingUser != null) {
                return Mono.error(new InfrastructureException(ErrorCode.FOUND.getMessage(), ErrorCode.FOUND));
            } else {
                return Mono.just(userDTO)
                        .map(dto -> modelMapper.map(dto, User.class))
                        .flatMap(savedEntity ->
                                Mono.just(savedEntity)
                                        .flatMap(user ->
                                                accountRepository.save(AccountDTO.builder()
                                                                .user(user)
                                                                .active(true)
                                                                .amount(0.0)
                                                                .build())
                                                        .thenReturn(modelMapper.map(user, UserDTO.class))
                                        )
                        );
            }
        });
    }


    @Override
    public Mono<UserDTO> findByUserId(String userId) {
        log.info("FindByUserId from userGatewayImpl {} ", userId);
        return Mono.justOrEmpty(userRepository.findByUserId(userId))
                .switchIfEmpty(Mono.error(new InfrastructureException(ErrorCode.NOT_FOUND.getMessage(), ErrorCode.NOT_FOUND)))
                .map(entity -> modelMapper.map(entity, UserDTO.class));
    }
}
