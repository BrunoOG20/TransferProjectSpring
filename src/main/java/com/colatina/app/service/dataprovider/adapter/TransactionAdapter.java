package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.configuration.mapper.TransactionMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.gateway.TransactionGateway;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.entity.TransactionEntity;
import com.colatina.app.service.dataprovider.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionAdapter implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final AccountMapper accountMapper;

    @Override
    public List<TransactionDomain> getAccountStatement(
            final Integer accountId,
            final LocalDateTime startDate,
            final LocalDateTime endDate
    ) {
        return transactionMapper.toDto(
                transactionRepository.findAllByAccountOriginIdAndCreatedAtBetween(accountId, startDate, endDate)
        );
    }

    @Override
    public List<TransactionDomain> getAllAccountStatementProcessed(final Integer accountId) {
        return transactionMapper.toDto(
                transactionRepository.findAllByAccountTransitionProcessed(accountId)
        );
    }

    @Override
    public List<TransactionDomain> getAllAccountStatementRefused(final Integer accountId) {
        return transactionMapper.toDto(
                transactionRepository.findAllByAccountTransitionRefused(accountId)
        );
    }

    @Override
    public List<TransactionDomain> getAllByAccountReceiveTransition(final Integer accountId) {
        return transactionMapper.toDto(
                transactionRepository.findAllByAccountReceiveTransition(accountId)
        );
    }

    @Override
    public TransactionDomain makeTransaction(AccountDomain sender, AccountDomain receiver, BigDecimal value) {
        AccountEntity senderEntity = accountMapper.toEntity(sender);
        AccountEntity receiverEntity = accountMapper.toEntity(receiver);

        TransactionEntity newTransaction = createTransactionEntity(senderEntity, receiverEntity, value);

        return transactionMapper.toDto(newTransaction);
    }

    @Override
    public TransactionDomain saveTransaction(TransactionDomain transactionDomain) {
        TransactionEntity transactionEntity = transactionMapper.toEntity(transactionDomain);

        transactionRepository.save(transactionEntity);

        return transactionMapper.toDto(transactionEntity);
    }

    private TransactionEntity createTransactionEntity(AccountEntity sender, AccountEntity receiver, BigDecimal value){
        TransactionEntity newTransaction = new TransactionEntity();

        newTransaction.setAccountOrigin(sender);
        newTransaction.setAccountDestination(receiver);
        newTransaction.setValue(value);
        newTransaction.setCreatedAt(LocalDateTime.now());
        newTransaction.setType("PIX");
        newTransaction.setStatus("WAITING_PROCESSING");

        return newTransaction;
    }

}
