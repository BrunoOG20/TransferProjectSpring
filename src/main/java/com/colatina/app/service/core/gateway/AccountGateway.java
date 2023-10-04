package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.WalletDomain;

import java.math.BigDecimal;

public interface AccountGateway {
    AccountDomain create(AccountDomain account);
    AccountDomain findById(Integer accountId);
    void update(AccountDomain accountDomain);
}
