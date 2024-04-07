package test.wallet.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import test.wallet.challenge.domain.model.BalanceDTO;
import test.wallet.challenge.domain.model.RechargeWalletDTO;
import test.wallet.challenge.domain.model.UserDTO;
import test.wallet.challenge.domain.services.ChallengeServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceImplTest {

    @InjectMocks
    private ChallengeServiceImpl challengeServiceImpl;

    @Test
    @DisplayName("Calculate Maximum")
    void testWallet() {
        UserDTO testCase3 = new UserDTO(null, "107528723", "José Manuel Burbano Prieto", "jmburbanopr@gmail.com", "3182010836");
        UserDTO testBalanceCase3 = new UserDTO(null, "107528723", "José Manuel Burbano Prieto", "jmburbanopr@gmail.com", "3182010836");

        // Usar block() para esperar la emisión del Mono y obtener el valor resultante
        UserDTO resultUserDTO = challengeServiceImpl.registerUser(testCase3).block();
        assertEquals(testBalanceCase3, resultUserDTO);

        BalanceDTO testCase1 = new BalanceDTO("107528723", "José Manuel Burbano Prieto", "Su saldo", 0.0);
        BalanceDTO resultBalanceDTO = challengeServiceImpl.balanceWallet("107528723", "3182010836").block();
        assertEquals(testCase1, resultBalanceDTO);

        RechargeWalletDTO testCase2 = new RechargeWalletDTO("107528723", "3182010836", 5000.00);
        BalanceDTO testCaseBalance2 = new BalanceDTO("107528723", "José Manuel Burbano Prieto", "Su saldo", 5000.00);
        BalanceDTO resultBalanceDTO2 = challengeServiceImpl.rechargeWallet(testCase2).block();
        assertEquals(testCaseBalance2, resultBalanceDTO2);
    }
}