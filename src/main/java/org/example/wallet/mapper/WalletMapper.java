package org.example.wallet.mapper;

import org.example.wallet.model.Wallet;
import org.example.wallet.model.dto.WalletDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Anatoliy Shikin
 */
@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletDto toDto(Wallet wallet);

    Wallet fromDto(WalletDto walletDto);
}
