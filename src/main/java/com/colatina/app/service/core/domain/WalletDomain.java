package com.colatina.app.service.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class WalletDomain {
    private Integer id;

    @NotNull
    private BigDecimal balance;
}
