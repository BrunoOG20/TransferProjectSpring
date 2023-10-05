package com.colatina.app.service.entrypoint.api;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.usecase.ChangeStatusUserCase;
import com.colatina.app.service.core.usecase.CreateAccountUseCase;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final ChangeStatusUserCase changeStatusUserCase;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AccountDomain data) {
        createAccountUseCase.execute(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable @NotNull @Positive Integer id) {
        changeStatusUserCase.execute(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
