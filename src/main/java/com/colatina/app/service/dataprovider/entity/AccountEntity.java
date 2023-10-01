package com.colatina.app.service.dataprovider.entity;

import com.colatina.app.service.core.domain.WalletDomain;
import liquibase.pro.packaged.A;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    @SequenceGenerator(name = "seq_account", allocationSize = 1, sequenceName = "seq_account")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "document")
    private String document;

    @Column(name = "status")
    private String status;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WalletEntity> wallets = new ArrayList<>();


}
