package org.example.settlement.mapper;

import org.example.settlement.DTO.AdditionalPropertiesDTO;
import org.example.settlement.DTO.InstanceArrangementDTO;
import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.DTO.request.RequestInstanceBodyDTO;
import org.example.settlement.service.RequiredFieldsProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


/**
 * Разбирает полученный запрос по полям во внутреннюю структуру. За одно должен проверить заполнение обязательных полей
 */
@Component
@RequiredArgsConstructor
public class InstanceRequestBodyMapper implements RequestBodyMapperInterface<RequestInstanceBodyDTO, InstanceBodyDTO>  {

    @Autowired
    private final AdditionalPropertiesListMapper additionalPropertiesMapper;

    @Autowired
    private final InstanceArrangementListMapper instanceArrangementListMapper;
    @Override
    public InstanceBodyDTO map(RequestInstanceBodyDTO request) {
        List<AdditionalPropertiesDTO> additionalPropertiesDTO =
                Optional.ofNullable(request.getAdditionalPropertiesVip())
                .map(additionalPropertiesMapper::map)
                .orElse(null);

        List<InstanceArrangementDTO> instanceArrangementDTOList =
                Optional.ofNullable(request.getInstanceArrangement())
                        .map(instanceArrangementListMapper::map)
                        .orElse(null);
        InstanceBodyDTO instanceBodyDTO = new InstanceBodyDTO(
                request.getInstanceId(),
                request.getProductType(),
                request.getProductCode(),
                request.getRegisterType(),
                request.getMdmCode(),
                request.getContractNumber(),
                request.getContractDate(),
                request.getPriority(),
                request.getInterestRatePenalty(),
                request.getMinimalBalance(),
                request.getThresholdAmount(),
                request.getAccountingDetails(),
                request.getRateType(),
                request.getTaxPercentageRate(),
                request.getTechnicalOverdraftLimitAmount(),
                request.getContractId(),
                request.getBranchCode(),
                request.getIsoCurrencyCode(),
                request.getReferenceCode(),
                additionalPropertiesDTO, //это список
                instanceArrangementDTOList
        );
        instanceBodyDTO = RequiredFieldsProcessor.process(instanceBodyDTO);
        return instanceBodyDTO;
    }
}
