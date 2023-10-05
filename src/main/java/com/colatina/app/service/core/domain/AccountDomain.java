package com.colatina.app.service.core.domain;

import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

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

    private WalletDomain walletDomain;

    public int getAge(){
        LocalDate currentDate = LocalDate.now();
        return Period.between(this.getBirthDate(), currentDate).getYears();
    }

    public boolean isAdult(){
        //Retorno 'True' se for maior que 18 anos
        return this.getAge() > 18;
    }

}
