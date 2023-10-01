package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountAdapter implements AccountGateway {

    private final AccountMapper mapper;
    private final AccountRepository repository;

    private final WalletAdapter walletAdapter;
    @Override
    public AccountDomain create(AccountDomain account) {
        AccountEntity entity = mapper.toEntity(account);

        repository.save(entity);
        System.out.println("Usuario criado");
        walletAdapter.create(entity);
        System.out.println("Carteira criada");

        return mapper.toDto(entity);
    }

    @Override
    public AccountDomain findById(int accountId) {
        AccountEntity entity = repository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Conta nao encontrada"));

        return mapper.toDto(entity);
    }

    @Override
    public AccountDomain update(AccountDomain account) {
        AccountEntity entity = mapper.toEntity(account);
        repository.save(entity);

        return mapper.toDto(entity);

    }

    @Override
    public BigDecimal findBalanceByAccountId(int accountId) {
        AccountEntity entity = repository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Conta nao encontrada"));

        return walletAdapter.getAllAccountWallets(entity.getId());
    }

}
