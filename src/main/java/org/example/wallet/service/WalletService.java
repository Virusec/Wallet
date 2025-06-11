package org.example.wallet.service;

import org.example.wallet.dictionary.OperationType;
import org.example.wallet.model.dto.WalletDto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public interface WalletService {
    WalletDto create();

    WalletDto operate(UUID id, OperationType operationType, BigDecimal amount);

    WalletDto getBalance(UUID id);
}
