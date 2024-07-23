package org.example.settlement.mapper;

import org.example.settlement.DTO.AccountDTO;
import org.example.settlement.DTO.request.RequestAccountDTO;
import org.example.settlement.service.RequiredFieldsProcessor;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements RequestBodyMapperInterface<RequestAccountDTO, AccountDTO> {
    @Override
    public AccountDTO map(RequestAccountDTO request) {
        AccountDTO accDTO = new AccountDTO(
                request.getInstanceId(),
                request.getRegistryTypeCode(),
                request.getAccountType(),
                request.getCurrencyCode(),
                request.getBranchCode(),
                request.getPriorityCode(),
                request.getMdmCode(),
                request.getClientCode(),
                request.getTrainRegion(),
                request.getCounter(),
                request.getSalesCode()
        );
        return RequiredFieldsProcessor.process(accDTO);
    }
}
