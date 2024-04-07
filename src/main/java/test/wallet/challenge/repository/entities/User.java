package test.wallet.challenge.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.wallet.common.infraestructure.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private Boolean active;

}
