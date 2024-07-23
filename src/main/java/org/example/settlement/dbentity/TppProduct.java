package org.example.settlement.dbentity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tpp_product")
@AllArgsConstructor
@NoArgsConstructor
public class TppProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger productCode;
    private BigInteger ClientId;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String number;

    private BigInteger priority;

    private Timestamp dateOfConclusion;
    private Timestamp startDateTime;
    private Timestamp startEndTime;

    private BigInteger days;

    private BigDecimal pentalRate;

    private BigDecimal nso;

    @Column
    private BigDecimal thresholdAmount;

    @Column(length = 50)
    private String requisiteType;

    @Column(length = 50)
    private String interestRateType;

    private BigDecimal taxRate;

    @Column(length = 100)
    private String reasonClose;

    @Column(length = 50)
    private String state;

    @OneToMany(mappedBy = "productId")
    private List<Agreement> agrementList = new ArrayList<>();

    public TppProduct(String productType ,
                       String contractNumber,
                       Timestamp contractDate,
                       Integer priority,
                       BigDecimal interestRatePenalty ,
                       BigDecimal thresholdAmount,
                       String rateType,
                      BigDecimal taxRate) {
        this.type = productType;
        this.number = contractNumber;
        this.dateOfConclusion = contractDate;
        this.priority = BigInteger.valueOf(priority);
        this.pentalRate = interestRatePenalty;
        this.thresholdAmount = thresholdAmount;
        this.interestRateType = rateType;
        this.taxRate = taxRate;
    }
}
