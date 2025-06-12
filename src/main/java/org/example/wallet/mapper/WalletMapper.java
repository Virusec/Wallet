package org.example.wallet.mapper;

import org.example.wallet.model.Wallet;
import org.example.wallet.model.dto.WalletResponseDto;
import org.mapstruct.Mapper;

/**
 * @author Anatoliy Shikin
 */
@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletResponseDto toDto(Wallet wallet);
}
