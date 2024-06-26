package test.wallet.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.wallet.challenge.repository.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Integer id;
    private Double amount;
    private User user;
    private Boolean active;

}
