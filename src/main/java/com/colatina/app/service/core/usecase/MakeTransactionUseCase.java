package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import com.colatina.app.service.core.domain.enumeration.TransactionStatus;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.core.gateway.TransactionGateway;
import com.colatina.app.service.core.gateway.WalletGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MakeTransactionUseCase {
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;
    private final WalletGateway walletGateway;

    public TransactionDomain execute(TransactionDomain transactionDomain){
        AccountDomain sender = accountGateway.findById(transactionDomain.getAccountOrigin().getId());
        AccountDomain receiver = accountGateway.findById(transactionDomain.getAccountDestination().getId());
        BigDecimal value = transactionDomain.getValue();

        if (!verifyIfStatusIsActive(sender) && !verifyIfStatusIsActive(receiver)){
            throw new BusinessException("Ambos usuarios precisam estar ATIVOS no sistema");
        }

        if (!verifyBalanceAccountSender(sender, value)){
            throw new BusinessException("Saldo insuficiente do remetente");
        }

        TransactionDomain newTransaction =  transactionGateway.makeTransaction(
                sender,
                receiver,
                value
        );

        try {
            BigDecimal newSenderBalance = walletGateway.debitTheAccount(sender, value);
            BigDecimal newReceiverBalance = walletGateway.creditTheAccount(receiver, value);

            walletGateway.updateAccountBalance(sender, newSenderBalance);
            walletGateway.updateAccountBalance(receiver, newReceiverBalance);

            newTransaction.setStatus(TransactionStatus.PROCESSED);

        } catch (BusinessException e){
            newTransaction.setStatus(TransactionStatus.REFUSED);
            throw new BusinessException("Ocorreu um erro durante a transacao ");
        }

        return transactionGateway.saveTransaction(newTransaction);

    }

    private boolean verifyIfStatusIsActive(AccountDomain account){
        return account.getStatus() == AccountStatus.ACTIVE;
    }

    private boolean verifyBalanceAccountSender(AccountDomain account, BigDecimal value){
        int result = accountGateway.getWalletBalance(account.getId()).compareTo(value);
        return result >= 0;
    }
}
