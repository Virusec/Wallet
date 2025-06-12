package org.example.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

/**
 * @author Anatoliy Shikin
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(BigDecimal balance, BigDecimal amount) {
        super("Current balance is: " + balance + ". Can't withdraw " + amount + " .");
    }
}
