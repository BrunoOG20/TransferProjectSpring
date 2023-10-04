package com.colatina.app.service.entrypoint.api;

import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.usecase.GetAccountStatementUseCase;
import com.colatina.app.service.core.usecase.MakeTransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final GetAccountStatementUseCase getAccountStatementUseCase;
    private final MakeTransactionUseCase makeTransactionUseCase;

    @GetMapping("/account-statement/{account_id}")
    public ResponseEntity<List<TransactionDomain>> getAccountStatement(@PathVariable("account_id") Integer accountId,
                                                                        @RequestHeader("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                                        @RequestHeader("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
        final List<TransactionDomain> accountStatement = getAccountStatementUseCase.getAccountStatement(accountId, startDate, endDate);
        return new ResponseEntity<>(accountStatement, accountStatement.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/account-statement-status-processed/{account_id}")
    public ResponseEntity<List<TransactionDomain>> getAccountStatementProcessed(@PathVariable("account_id") Integer accountId) {
        final List<TransactionDomain> accountStatement = getAccountStatementUseCase.getAllAccountStatementProcessed(accountId);
        return new ResponseEntity<>(accountStatement, accountStatement.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/account-statement-status-not-processed/{account_id}")
    public ResponseEntity<List<TransactionDomain>> getAccountStatementRefused(@PathVariable("account_id") Integer accountId) {
        final List<TransactionDomain> accountStatement = getAccountStatementUseCase.getAllAccountStatementRefused(accountId);
        return new ResponseEntity<>(accountStatement, accountStatement.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/account-statement-receive/{account_id}")
    public ResponseEntity<List<TransactionDomain>> getAccountStatementReceive(@PathVariable("account_id") Integer accountId) {
        final List<TransactionDomain> accountStatement = getAccountStatementUseCase.getAllAccountReceiveTransition(accountId);
        return new ResponseEntity<>(accountStatement, accountStatement.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionDomain> create(@RequestBody @Valid TransactionDomain data) {
        TransactionDomain newTransaction = makeTransactionUseCase.execute(data);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

}
