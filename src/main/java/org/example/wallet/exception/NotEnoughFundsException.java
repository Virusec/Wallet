package org.example.wallet.exception;

import java.math.BigDecimal;

/**
 * @author Anatoliy Shikin
 */
public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(BigDecimal balance, BigDecimal amount) {
        super("Current balance is: " + balance + ". Can't withdraw " + amount + " .");
    }
}
