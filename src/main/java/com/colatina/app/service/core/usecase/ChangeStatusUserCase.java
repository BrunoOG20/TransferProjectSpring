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

    public void execute(int accountId){
        // Buscar Usuario
        AccountDomain findedAccount = accountGateway.findById(accountId);

        // Se a conta estiver Ativada, sera bloqueada
        if (findedAccount.getStatus() == AccountStatus.ACTIVE){
            findedAccount.blockAccount();
        } else {
            // Caso contrario sera Ativada
            findedAccount.activeAccount();
        }

        // Atualizar conta com novo Status
        accountGateway.update(findedAccount);
    }
}
