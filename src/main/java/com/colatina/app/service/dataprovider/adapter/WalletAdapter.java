package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.WalletGateway;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletAdapter implements WalletGateway {
    private final WalletRepository walletRepository;

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    @Override
    public void createWalletToAccount(AccountDomain accountDomain) {
        WalletEntity walletEntity = new WalletEntity();
        AccountEntity accountEntity = accountMapper.toEntity(accountDomain);

        walletEntity.setAccount(accountEntity);
        walletEntity.setBalance(BigDecimal.ZERO);
        accountEntity.setWallet(walletEntity);

        accountRepository.save(accountEntity);
        walletRepository.save(walletEntity);

    }

    @Override
    public BigDecimal getWalletBalance(Integer accountId) {
        AccountEntity entity = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Conta nao encontrada"));

        return walletRepository.findBalanceByAccountId(accountId);
    }

    @Override
    public BigDecimal debitTheAccount(AccountDomain account, BigDecimal value){
        AccountEntity accountEntity = accountMapper.toEntity(account);
        BigDecimal currentBalance = walletRepository.findBalanceByAccountId(accountEntity.getId());

        return currentBalance.subtract(value);
    }

    @Override
    public BigDecimal creditTheAccount(AccountDomain account, BigDecimal value){
        AccountEntity accountEntity = accountMapper.toEntity(account);
        BigDecimal currentBalance = walletRepository.findBalanceByAccountId(accountEntity.getId());

        return currentBalance.add(value);
    }

    @Override
    public void updateAccountBalance(AccountDomain accountDomain, BigDecimal value){
        AccountEntity accountEntity = accountMapper.toEntity(accountDomain);
        WalletEntity wallet = walletRepository.findWalletByAccountId(accountEntity.getId());

        accountEntity.setWallet(wallet);
        accountEntity.getWallet().setBalance(value);
        accountRepository.save(accountEntity);
    }

}
