package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import com.colatina.app.service.core.gateway.AccountGateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeStatusUserCase {

    private final AccountGateway accountGateway;

    public void execute(Integer accountId){
        AccountDomain findedAccount = accountGateway.findById(accountId);

        if (findedAccount.getStatus() == AccountStatus.ACTIVE){
            findedAccount.setStatus(AccountStatus.BLOCKED);
        } else {
            findedAccount.setStatus(AccountStatus.ACTIVE);
        }

        accountGateway.update(findedAccount);
    }
}
