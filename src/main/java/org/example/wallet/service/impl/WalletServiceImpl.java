package org.example.wallet.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wallet.dictionary.OperationType;
import org.example.wallet.exception.NotEnoughFundsException;
import org.example.wallet.exception.WalletNotFoundException;
import org.example.wallet.mapper.WalletMapper;
import org.example.wallet.model.Wallet;
import org.example.wallet.model.WalletOperation;
import org.example.wallet.model.dto.WalletRequestDto;
import org.example.wallet.model.dto.WalletResponseDto;
import org.example.wallet.repository.WalletOperationRepository;
import org.example.wallet.repository.WalletRepository;
import org.example.wallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletOperationRepository walletOperationRepository;
    private final WalletMapper walletMapper;

    @Override
    public WalletResponseDto create() {
        Wallet wallet = new Wallet();
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toDto(savedWallet);
    }

    @Override
    @Transactional
    public WalletResponseDto operate(WalletRequestDto request) {
        Wallet wallet = getWalletOrThrowException(request.walletId());
        BigDecimal amount = request.amount();
        OperationType type = request.operationType();

        if (type == OperationType.WITHDRAW
                && wallet.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsException(wallet.getBalance(), amount);
        }

        BigDecimal newBalance =
                type == OperationType.DEPOSIT
                        ? wallet.getBalance().add(amount)
                        : wallet.getBalance().subtract(amount);
        wallet.setBalance(newBalance);

        WalletOperation operation = WalletOperation.builder()
                .wallet(wallet)
                .operationType(type)
                .amount(amount)
                .registrationDate(ZonedDateTime.now())
                .build();
        walletOperationRepository.save(operation);

        return walletMapper.toDto(wallet);
    }

    /**
     * Читающий метод: не вносит изменений в БД.
     * readOnly = true отключает механизм обнаружения изменений (dirty checking)
     * и сброс (flush) контекста персистентности, что оптимизирует работу и снижает нагрузку на БД.
     */
    @Override
    @Transactional(readOnly = true)
    public WalletResponseDto getBalance(UUID id) {
        Wallet wallet = getWalletOrThrowException(id);
        return walletMapper.toDto(wallet);
    }

    private Wallet getWalletOrThrowException(UUID id) {
        return walletRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }
}
