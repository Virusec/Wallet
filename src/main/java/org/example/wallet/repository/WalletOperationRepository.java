package org.example.wallet.repository;

import org.example.wallet.model.WalletOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Anatoliy Shikin
 */
public interface WalletOperationRepository extends JpaRepository<WalletOperation, UUID> {
}
