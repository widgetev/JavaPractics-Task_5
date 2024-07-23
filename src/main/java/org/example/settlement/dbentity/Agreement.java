package org.example.settlement.dbentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "agreement")
@NoArgsConstructor
public class Agreement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private TppProduct productId;

    @Column(length = 50)
    private String generalAgreementId;

    @Column(length = 50)
    private String suplementaryAgreementId;

    @Column(length = 50)
    private String agreementType;

    private Long shedulerJobId;

    @Column(length = 50)
    private String number;

    private Timestamp openingDate;

    private Timestamp closingDate;

    private Timestamp cancelDate;

    private Long validityDuration;

    @Column(length = 100)
    private String cancellationReason;

    @Column(length = 50)
    private String status;

    private Timestamp interestCalculationDate;

    private Float interestRate;
    private Float coefficient;

    @Column(length = 50)
    private String coefficient_action;

    private Float minimumInterestRate;
    private Float minimumInterestRateCoefficient;

    @Column(length = 50)
    private String minimumInterestRateCoefficientAction;

    private Float maximalInterestRate;
    private Float maximalInterestRateCoefficient;

    @Column(length = 50)
    private String maximalInterestRateCoefficientAction;
    public Agreement(TppProduct productId, String generalAgreementId, String suplementaryAgreementId, String agreementType,
                     Long shedulerJobId, String number, Timestamp openingDate, Timestamp closingDate, Timestamp cancelDate,
                     Long validityDuration, String cancellationReason, String status, Timestamp interestCalculationDate,
                     Float interestRate, Float coefficient, String coefficient_action, Float minimumInterestRate,
                     Float minimumInterestRateCoefficient, String minimumInterestRateCoefficientAction,
                     Float maximalInterestRate, Float maximalInterestRateCoefficient, String maximalInterestRateCoefficientAction) {
        this.productId = productId;
        this.generalAgreementId = generalAgreementId;
        this.suplementaryAgreementId = suplementaryAgreementId;
        this.agreementType = agreementType;
        this.shedulerJobId = shedulerJobId;
        this.number = number;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.cancelDate = cancelDate;
        this.validityDuration = validityDuration;
        this.cancellationReason = cancellationReason;
        this.status = status;
        this.interestCalculationDate = interestCalculationDate;
        this.interestRate = interestRate;
        this.coefficient = coefficient;
        this.coefficient_action = coefficient_action;
        this.minimumInterestRate = minimumInterestRate;
        this.minimumInterestRateCoefficient = minimumInterestRateCoefficient;
        this.minimumInterestRateCoefficientAction = minimumInterestRateCoefficientAction;
        this.maximalInterestRate = maximalInterestRate;
        this.maximalInterestRateCoefficient = maximalInterestRateCoefficient;
        this.maximalInterestRateCoefficientAction = maximalInterestRateCoefficientAction;
    }
}
