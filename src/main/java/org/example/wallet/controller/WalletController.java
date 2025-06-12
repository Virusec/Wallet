package org.example.wallet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.wallet.model.dto.WalletRequestDto;
import org.example.wallet.model.dto.WalletResponseDto;
import org.example.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/create")
    public ResponseEntity<WalletResponseDto> create() {
        WalletResponseDto response = walletService.create();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponseDto> getWallet(@PathVariable UUID id) {
        WalletResponseDto walletResponseDto = walletService.getBalance(id);
        return ResponseEntity.status(HttpStatus.OK).body(walletResponseDto);
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletResponseDto> operate(@RequestBody @Valid WalletRequestDto request) {
        WalletResponseDto response = walletService.operate(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
