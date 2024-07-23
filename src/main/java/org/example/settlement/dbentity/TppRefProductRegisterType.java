package org.example.settlement.dbentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@Table(name = "tpp_ref_product_register_type")
public class TppRefProductRegisterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_Id")
    private Long internalId;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TppProductRegister> tppProductRegisterList = new ArrayList<>();

    @Column(length = 100, unique = true)
    private String value;

    @Column(length = 100)
    private String register_type_name;

    @ManyToOne
    @JoinColumn(name="product_class_code", referencedColumnName = "value")
    private TppRefProductClass productClassCode;

    private Timestamp register_type_start_date;

    private Timestamp register_type_end_date;

    @ManyToOne
    @JoinColumn(name="account_type", referencedColumnName = "value")
    private TppRefAccountType accountType;
}
