package org.example.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.wallet.controller.WalletController;
import org.example.wallet.model.dto.WalletResponseDto;
import org.example.wallet.model.dto.WalletRequestDto;
import org.example.wallet.dictionary.OperationType;
import org.example.wallet.exception.GlobalExceptionHandler;
import org.example.wallet.exception.NotEnoughFundsException;
import org.example.wallet.exception.WalletNotFoundException;
import org.example.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Anatoliy Shikin
 */
@WebMvcTest(WalletController.class)
@Import(GlobalExceptionHandler.class)
public class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String NOW_STR = "2025-06-12T10:00:00Z";
    private static final ZonedDateTime NOW = ZonedDateTime.parse(NOW_STR);

    @Test
    void create_ShouldReturnCreated() throws Exception {
        UUID id = UUID.randomUUID();
        WalletResponseDto dto = new WalletResponseDto(id, BigDecimal.ZERO, NOW);

        Mockito.when(walletService.create()).thenReturn(dto);

        mockMvc.perform(post("/api/v1/create"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(jsonPath("$.registrationDate").value(NOW_STR));
    }

    @Test
    void getWallet_ShouldReturnOk() throws Exception {
        UUID id = UUID.randomUUID();
        WalletResponseDto dto = new WalletResponseDto(id, new BigDecimal("100.50"), NOW);

        Mockito.when(walletService.getBalance(id)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/wallets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.balance").value(100.50))
                .andExpect(jsonPath("$.registrationDate").value(NOW_STR));
    }

    @Test
    void getWallet_NotFound_ShouldReturn404() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(walletService.getBalance(id))
                .thenThrow(new WalletNotFoundException(id));

        mockMvc.perform(get("/api/v1/wallets/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.WALLET_NOT_FOUND")
                        .value("Wallet with id " + id + " not found."));
    }

    @Test
    void operate_Deposit_ShouldReturnOk() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("50");
        WalletRequestDto req = new WalletRequestDto(id, OperationType.DEPOSIT, amount);
        WalletResponseDto dto = new WalletResponseDto(id, amount, NOW);

        Mockito.when(walletService.operate(any(WalletRequestDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.balance").value(50))
                .andExpect(jsonPath("$.registrationDate").value(NOW_STR));
    }

    @Test
    void operate_Withdraw_ShouldReturnOk() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("30");
        WalletRequestDto req = new WalletRequestDto(id, OperationType.WITHDRAW, amount);
        WalletResponseDto dto = new WalletResponseDto(id, new BigDecimal("70"), NOW);

        Mockito.when(walletService.operate(any(WalletRequestDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(70))
                .andExpect(jsonPath("$.registrationDate").value(NOW_STR));
    }

    @Test
    void operate_Withdraw_NotEnoughFunds_ShouldReturn400() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1000");
        WalletRequestDto req = new WalletRequestDto(id, OperationType.WITHDRAW, amount);
        NotEnoughFundsException ex =
                new NotEnoughFundsException(new BigDecimal("100"), amount);

        Mockito.when(walletService.operate(any(WalletRequestDto.class))).thenThrow(ex);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.NOT_ENOUGH_FUNDS")
                        .value("Current balance is: 100. Can't withdraw 1000 ."));
    }

    @Test
    void operate_InvalidRequest_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.INVALID_REQUEST").exists());
    }
}
