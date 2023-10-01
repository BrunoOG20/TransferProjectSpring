package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.WalletMapper;
import com.colatina.app.service.core.gateway.WalletGateway;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import com.colatina.app.service.dataprovider.repository.WalletRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletAdapter implements WalletGateway {
    private final WalletRepository repository;
    private final WalletMapper mapper;

    @Override
    public void create(AccountEntity account) {
        WalletEntity wallet = new WalletEntity();

        wallet.setAccount(account);
        wallet.setBalance(BigDecimal.ZERO);

        repository.save(wallet);

    }
    @Override
    public BigDecimal getAllAccountWallets(int accountId) {
        return repository.findBalanceByAccountId(accountId);
    }
}
