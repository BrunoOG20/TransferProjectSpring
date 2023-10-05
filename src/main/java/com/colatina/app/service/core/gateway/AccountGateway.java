package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;

import java.math.BigDecimal;

public interface AccountGateway {
    AccountDomain create(AccountDomain account);
    AccountDomain findById(Integer accountId);
    void update(AccountDomain accountDomain);
    BigDecimal getWalletBalance(Integer accountId);

}
