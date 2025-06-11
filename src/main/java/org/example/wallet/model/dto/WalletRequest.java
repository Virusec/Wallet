package org.example.wallet.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.wallet.dictionary.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public record WalletRequest(
        @NotNull
        UUID id,
        @NotNull
        OperationType operationType,
        @Min(1)
        BigDecimal amount
) {
}
