package org.example.settlement.dbentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Setter @Getter
@Table(name = "tpp_product_register")
@RequiredArgsConstructor
@AllArgsConstructor
public class TppProductRegister {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name="type", referencedColumnName = "value")
    TppRefProductRegisterType type;

    private Long account;

    @Column(length = 30)
    private String currency_code;

    @Column(length = 50)
    private String state;

    @Column(length = 25)
    //@Size(min=25, max = 25 )
    private String account_number;

    public TppProductRegister(Long productId, TppRefProductRegisterType type, Long account, String currency_code, String state, String account_number) {
        this.productId = productId;
        this.type = type;
        this.account = account;
        this.currency_code = currency_code;
        this.state = state;
        this.account_number = account_number;
    }
}
