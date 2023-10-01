package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.dataprovider.entity.AccountEntity;

import java.math.BigDecimal;

public interface WalletGateway {
    void create(AccountEntity account);
    BigDecimal getAllAccountWallets(int accountId);
}


