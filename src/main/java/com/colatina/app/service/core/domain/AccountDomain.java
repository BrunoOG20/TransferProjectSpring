package com.colatina.app.service.core.domain;

import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccountDomain {

    private Integer id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 100)
    private String lastName;

    @NotNull
    @Size(min = 11, max = 11)
    private String document;

    @NotNull
    private AccountStatus status;

    @NotNull
    @Past
    private LocalDate birthDate;

    public boolean isAdult(){
        // Obter a Data Atual
        LocalDate currentDate = LocalDate.now();

        // Obter a diferenca entre as duas datas
        int age = Period.between(this.getBirthDate(), currentDate).getYears();

        //Retorno 'True' se for maior que 18 anos
        return age > 18;
    }

    public void blockAccount() {
        this.status = AccountStatus.BLOCKED;
    }

    public void activeAccount() {
        this.status = AccountStatus.ACTIVE;
    }

    public void inactiveAccount() {
        this.status = AccountStatus.INACTIVE;
    }
}
