package org.example.settlement.dbentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@Entity
@Table(name = "account_pool")
public class AccountPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "accountPoolId")
    List<Account> accountList= new ArrayList<>();

    @Column(length = 50, name = "branch_code")
    private String branchCode;

    @Column(length = 30, name = "currency_code")
    private String currencyCode;

    @Column(length = 50, name = "mdm_code")
    private String mdmCode;

    @Column(length = 30, name = "priority_code")
    private String priorityCode;

    @Column(length = 50, name = "registry_type_code")
    private String registryTypeCode;

}
