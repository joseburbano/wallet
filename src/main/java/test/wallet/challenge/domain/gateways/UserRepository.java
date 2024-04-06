package test.wallet.challenge.domain.gateways;
import reactor.core.publisher.Mono;
import test.wallet.challenge.domain.model.UserDTO;

public interface UserRepository {

    Mono<UserDTO> save(UserDTO userDTO);

    Mono<UserDTO> findByUserId(String id);
}
