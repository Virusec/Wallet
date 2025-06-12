package org.example.wallet.model.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public record WalletResponseDto(

        UUID id,

        BigDecimal balance,

        ZonedDateTime registrationDate
) {
}
