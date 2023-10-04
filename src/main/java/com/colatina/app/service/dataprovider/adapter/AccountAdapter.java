package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.configuration.mapper.TransactionMapper;
import com.colatina.app.service.configuration.mapper.WalletMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.WalletDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.dataprovider.entity.TransactionEntity;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.repository.TransactionRepository;
import com.colatina.app.service.dataprovider.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountAdapter implements AccountGateway {

    private final AccountMapper mapper;
    private final AccountRepository repository;

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

}
