package org.example.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.example.wallet.model.dto.WalletDto;
import org.example.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<WalletDto> create() {
        WalletDto walletDto = walletService.create();
        return ResponseEntity.status(HttpStatus.CREATED).body(walletDto);
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable UUID id) {
        WalletDto walletDto = walletService.getBalance(id);
        return ResponseEntity.ok(walletDto);
    }
}
