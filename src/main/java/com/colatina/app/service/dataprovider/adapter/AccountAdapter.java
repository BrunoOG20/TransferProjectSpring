package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountAdapter implements AccountGateway {

    private final AccountMapper mapper;
    private final AccountRepository repository;

    private final WalletRepository walletRepository;
    @Override
    public AccountDomain create(AccountDomain account) {
        AccountEntity entity = mapper.toEntity(account);

        repository.save(entity);

        return mapper.toDto(entity);
    }

    @Override
    public AccountDomain findById(Integer accountId) {
        AccountEntity entity = repository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Conta nao encontrada"));

        return mapper.toDto(entity);
    }

    @Override
    public void update(AccountDomain account) {
        AccountEntity entity = mapper.toEntity(account);
        repository.save(entity);

    }
    @Override
    public BigDecimal getWalletBalance(Integer accountId) {
        AccountEntity entity = repository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Conta nao encontrada"));

        return walletRepository.findBalanceByAccountId(accountId);
    }

}
