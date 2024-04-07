package test.wallet.challenge.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "users", schema = "wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    private Integer id;

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
