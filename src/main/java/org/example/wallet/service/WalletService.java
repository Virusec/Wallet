package org.example.wallet.service;

import org.example.wallet.model.dto.WalletRequestDto;
import org.example.wallet.model.dto.WalletResponseDto;

import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public interface WalletService {
    WalletResponseDto create();

    WalletResponseDto operate(WalletRequestDto request);

    WalletResponseDto getBalance(UUID id);
}
