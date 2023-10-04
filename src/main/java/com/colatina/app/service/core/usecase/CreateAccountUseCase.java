package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.core.gateway.NegativeCpfGateway;
import com.colatina.app.service.core.gateway.WalletGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountGateway accountGateway;
    private final WalletGateway walletGateway;
    private final NegativeCpfGateway negativeCpfGateway;

    public void execute(AccountDomain account) {

        // Verificar se o Usuario tem a idade maior que 18 anos
        if (!account.isAdult()){
            throw new BusinessException("Menor de Idade");
        }

        // Verificar se o CPF e valido
        if (negativeCpfGateway.isNegativeCpf(account.getDocument())){
            throw new BusinessException("Cpf Negativado");
        }

        // Criar conta
        AccountDomain newAccount = accountGateway.create(account);
        walletGateway.createWalletToAccount(newAccount);
    }

}
