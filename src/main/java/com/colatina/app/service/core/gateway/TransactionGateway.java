package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.AccountInfoDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.dataprovider.entity.AccountEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionGateway {

    List<TransactionDomain> getAccountStatement(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionDomain> getAllAccountStatementProcessed(Integer accountId);

    List<TransactionDomain> getAllAccountStatementRefused(Integer accountId);

    List<TransactionDomain> getAllByAccountReceiveTransition(Integer accountId);

    TransactionDomain makeTransaction(AccountDomain sender, AccountDomain receiver, BigDecimal value);

    void saveTransaction(TransactionDomain transactionDomain);

}
