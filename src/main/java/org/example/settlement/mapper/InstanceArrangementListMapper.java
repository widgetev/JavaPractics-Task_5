package org.example.settlement.mapper;

import org.example.settlement.DTO.InstanceArrangementDTO;
import org.example.settlement.DTO.request.RequestInstanceArrangementDTO;
import org.example.settlement.service.RequiredFieldsProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstanceArrangementListMapper implements RequestBodyMapperInterface<List<RequestInstanceArrangementDTO>, List<InstanceArrangementDTO>>{
    @Override
    public List<InstanceArrangementDTO> map(List<RequestInstanceArrangementDTO> request) {
        List<InstanceArrangementDTO> tmp = new ArrayList<>();
        for (RequestInstanceArrangementDTO agr :request) {
            InstanceArrangementDTO instanceArrangementDTO = new InstanceArrangementDTO(
                    agr.generalAgreementId(),
                    agr.supplementaryAgreementId(),
                    agr.arrangementType(),
                    agr.shedulerJobId(),
                    agr.number() ,
                    agr.openingDate(),
                    agr.closingDate(),
                    agr.cancelDate(),
                    agr.validityDuration(), agr.cancellationReason(), agr.Status(), agr.interestCalculationDate(),
                    agr.interestRate(),
                    agr.coefficient(),
                    agr.coefficientAction(),
                    agr.minimumInterestRate(),
                    agr.minimumInterestRateCoefficient(),
                    agr.minimumInterestRateCoefficientAction(),
                    agr.maximalnterestRate(),
                    agr.maximalnterestRateCoefficient(),
                    agr.maximalnterestRateCoefficientAction()
            );
            instanceArrangementDTO = RequiredFieldsProcessor.process(instanceArrangementDTO);
            tmp.add(instanceArrangementDTO);
        }

    return tmp;
    };
}
