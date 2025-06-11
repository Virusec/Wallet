package org.example.wallet.exception;

import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID id) {
        super("Wallet with id " + id + " not found.");
    }
}
