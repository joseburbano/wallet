package test.wallet.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceDTO {

    @NotNull(message = "{common.field.required}")
    private String userId;

    @NotNull(message = "{common.field.required}")
    private String fullName;

    @NotNull(message = "{common.field.required}")
    private String message;

    @NotNull(message = "{common.field.required}")
    private Double amount;

}
