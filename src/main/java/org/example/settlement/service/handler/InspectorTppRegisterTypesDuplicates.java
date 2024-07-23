package org.example.settlement.service.handler;

import org.example.settlement.DTO.AccountDTO;
import org.example.settlement.dbentity.TppProductRegister;
import org.example.settlement.repository.TppProductRegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Order(10)
@Qualifier("accountOperatorList")
public class InspectorTppRegisterTypesDuplicates implements UnaryOperator<AccountDTO> {
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;

    @Override
    public AccountDTO apply(AccountDTO accountDTO) {
        List<TppProductRegister> tppProductRegisterList = tppProductRegisterRepo
                .findAllByProductIdAndType(accountDTO.getInstanceId(), accountDTO.getRegistryTypeCode());
        if(!tppProductRegisterList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Параметр registryTypeCode тип регистра " + accountDTO.getRegistryTypeCode() +
                            " уже существует для ЭП с ИД  " + accountDTO.getInstanceId() +".");
        }
        return accountDTO;
    }
}
