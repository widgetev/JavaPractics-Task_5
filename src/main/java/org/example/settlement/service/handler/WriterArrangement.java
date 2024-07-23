package org.example.settlement.service.handler;

import org.example.settlement.DTO.InstanceArrangementDTO;
import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.dbentity.Agreement;
import org.example.settlement.dbentity.TppProduct;
import org.example.settlement.repository.AgreementRepo;
import org.example.settlement.repository.TppProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.function.UnaryOperator;

@Component
@Order(30)
@Qualifier("instanceUpdateOperations")
public class WriterArrangement implements UnaryOperator<InstanceBodyDTO> {

    @Autowired
    TppProductRepo tppProductRepo;

    @Autowired
    AgreementRepo agreementRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
        System.out.println("writerArangement");
        for (InstanceArrangementDTO arrangementDTO: instanceBodyDTO.getInstanceArrangement()) {
            TppProduct tppProduct = tppProductRepo.findById(Long.valueOf(instanceBodyDTO.getInstanceId()))
                    .orElse(null);

            Agreement agreement=new Agreement( tppProduct,
                    arrangementDTO.getGeneralAgreementId(),//            this.generalAgreementId = generalAgreementId;
                    arrangementDTO.getSupplementaryAgreementId(), //            this.suplementaryAgreementId = suplementaryAgreementId;
                    arrangementDTO.getArrangementType(), //            this.agreementType = agreementType;
                    arrangementDTO.getShedulerJobId(), //            this.shedulerJobId = shedulerJobId;
                    arrangementDTO.getNumber(), //            this.number = number;
                    date2Timestamp(arrangementDTO.getOpeningDate()), //            this.openingDate = openingDate;
                    date2Timestamp(arrangementDTO.getClosingDate()), //            this.closingDate = closingDate;
                    date2Timestamp(arrangementDTO.getCancelDate()), //            this.cancelDate = cancelDate;
                    arrangementDTO.getValidityDuration(), //            this.validityDuration = validityDuration;
                    arrangementDTO.getCancellationReason(), //            this.cancellationReason = cancellationReason;
                    arrangementDTO.getStatus(), //            this.status = status;
                    date2Timestamp(arrangementDTO.getInterestCalculationDate()), //            this.interestCalculationDate = interestCalculationDate;
                    arrangementDTO.getInterestRate(), //            this.interestRate = interestRate;
                    arrangementDTO.getCoefficient(), //            this.coefficient = coefficient;
                    arrangementDTO.getCoefficientAction(), //            this.coefficient_action = coefficient_action;
                    arrangementDTO.getMinimumInterestRate(),//            this.minimumInterestRate = minimumInterestRate;
                    arrangementDTO.getMinimumInterestRateCoefficient(),//            this.minimumInterestRateCoefficient = minimumInterestRateCoefficient;
                    arrangementDTO.getMinimumInterestRateCoefficientAction(), //            this.minimumInterestRateCoefficientAction = minimumInterestRateCoefficientAction;
                    arrangementDTO.getMaximalnterestRate(), //            this.maximalInterestRate = maximalInterestRate;
                    arrangementDTO.getMaximalnterestRateCoefficient(),//            this.maximalInterestRateCoefficient = maximalInterestRateCoefficient;
                    arrangementDTO.getMaximalnterestRateCoefficientAction()//            this.maximalInterestRateCoefficientAction = maximalInterestRateCoefficientAction;
            );

            agreementRepo.save(agreement);
        }
        return instanceBodyDTO;
    }

    private Timestamp date2Timestamp(Date date){
        return (null != date) ? new Timestamp(date.getTime()) : null;
    }
}
