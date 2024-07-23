package org.example.settlement.dbentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_pool_id", referencedColumnName = "id")
    private AccountPool accountPoolId;

    @Column(length = 25)
    private String account_number;
    private Boolean bussy;
}
