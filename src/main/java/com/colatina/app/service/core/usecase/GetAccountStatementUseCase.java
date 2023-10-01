package com.colatina.app.service.core.usecase;


import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.TransactionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAccountStatementUseCase {

    private final TransactionGateway transactionGateway;

    public List<TransactionDomain> getAccountStatement(Integer accountId, LocalDateTime startDate, LocalDateTime endDate) {

        if (accountId == null || accountId <= 0) {
            throw new IllegalArgumentException("O accountId deve ser um número positivo válido.");
        }

        if (startDate == null) {
            throw new BusinessException("A data de início não pode ser nula.");
        }

        if (endDate == null) {
            throw new BusinessException("A data de término não pode ser nula.");
        }

        if (startDate.isAfter(endDate)) {
            throw new BusinessException("A data de início não pode ser posterior à data de término.");
        }

        return transactionGateway.getAccountStatement(accountId, startDate, endDate);
    }

}
