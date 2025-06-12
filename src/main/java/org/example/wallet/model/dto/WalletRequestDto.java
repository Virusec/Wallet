package org.example.wallet.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.wallet.dictionary.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public record WalletRequestDto(

        @NotNull
        UUID walletId,

        @NotNull
        OperationType operationType,

        @Positive
        @NotNull
        BigDecimal amount
) {
}
