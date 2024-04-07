package test.wallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;
import test.wallet.challenge.controllers.ChallengeController;
import test.wallet.challenge.domain.model.BalanceDTO;
import test.wallet.challenge.domain.model.RechargeWalletDTO;
import test.wallet.challenge.domain.model.UserDTO;
import test.wallet.challenge.domain.services.ChallengeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ChallengeControllerTest {

    @Mock
    private ChallengeService challengeService;
    @InjectMocks
    private ChallengeController challengeController;
    private MockMvc mockMvc;
    private Mono<BalanceDTO> balanceDTO;
    private Mono<RechargeWalletDTO> rechargeWalletDTO;
    private Mono<UserDTO> userDTO;


    @BeforeEach
    void setUp() {
        userDTO = Mono.just(UserDTO.builder()
                .email("jmburbanopr@gmail.com")
                .phone("3182010836")
                .fullName("José Manuel Burbano Prieto")
                .userId("107528723")
                .build());

        rechargeWalletDTO = Mono.just(RechargeWalletDTO.builder()
                .phone("3182010836")
                .userId("107528723")
                .amount(5000.00)
                .build());

        balanceDTO = Mono.just(BalanceDTO.builder()
                .userId("107528723")
                .fullName("José Manuel Burbano Prieto")
                .message("Su saldo")
                .amount(5000.00)
                .build());

        mockMvc = MockMvcBuilders.standaloneSetup(challengeController)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver(),
                        new SpecificationArgumentResolver())
                .build();

    }


    @Test
    @DisplayName("Register User Post")
    void testRegisterUserPost() throws Exception {
        when(challengeService.registerUser(any())).thenReturn(userDTO);

        mockMvc.perform(post("/v1/challenge/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Register User Post")
    void testCalculateMaximumPost() throws Exception {
        when(challengeService.rechargeWallet(any())).thenReturn(balanceDTO);

        mockMvc.perform(post("/v1/challenge/wallet/recharge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rechargeWalletDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Balance Wallet Get")
    void testCalculateMaximumGet() throws Exception {
        when(challengeService.balanceWallet("107528723", "3182010836")).thenReturn(balanceDTO);

        mockMvc.perform(get("/v1/challenge/wallet/balance")
                        .param("userId", "107528723")
                        .param("phone", "3182010836")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}