package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;

import java.math.BigDecimal;

public interface WalletGateway {
    void createWalletToAccount(AccountDomain accountDomain);
    BigDecimal getWalletBalance(Integer accountId);
    BigDecimal debitTheAccount(AccountDomain accountDomain, BigDecimal value);
    BigDecimal creditTheAccount(AccountDomain accountDomain, BigDecimal value);
    void updateAccountBalance(AccountDomain accountDomain, BigDecimal value);
}
