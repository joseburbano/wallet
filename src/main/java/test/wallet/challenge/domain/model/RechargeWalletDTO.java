package test.wallet.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RechargeWalletDTO {

    @NotNull(message = "{common.field.required}")
    private String userId;

    @NotNull(message = "{common.field.required}")
    private String phone;

    @NotNull(message = "{common.field.required}")
    @DecimalMin(value = "0", message = "El valor debe ser positivo")
    private Double amount;
}
