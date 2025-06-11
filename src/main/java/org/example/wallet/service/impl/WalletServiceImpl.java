package org.example.wallet.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.wallet.dictionary.OperationType;
import org.example.wallet.exception.WalletNotFoundException;
import org.example.wallet.mapper.WalletMapper;
import org.example.wallet.model.Wallet;
import org.example.wallet.model.dto.WalletDto;
import org.example.wallet.repository.WalletOperationRepository;
import org.example.wallet.repository.WalletRepository;
import org.example.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletOperationRepository walletOperationRepository;
    private final WalletMapper walletMapper;

    @Override
    public WalletDto create() {
        Wallet wallet = new Wallet();
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toDto(savedWallet);
    }

    @Override
    public WalletDto operate(UUID id, OperationType operationType, BigDecimal amount) {

        return null;
    }

    @Override
    public WalletDto getBalance(UUID id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        return walletMapper.toDto(wallet);
    }
}
