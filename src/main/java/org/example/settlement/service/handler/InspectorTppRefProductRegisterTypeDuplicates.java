package org.example.settlement.service.handler;

import org.example.settlement.DTO.AccountDTO;
import org.example.settlement.dbentity.TppRefProductRegisterType;
import org.example.settlement.repository.TppRefProductRegisterTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.UnaryOperator;

@Component
@Order(20)
@Qualifier("accountOperatorList")
public class InspectorTppRefProductRegisterTypeDuplicates implements UnaryOperator<AccountDTO> {
    @Autowired
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;

    @Override
    public AccountDTO apply(AccountDTO accountDTO) {
        TppRefProductRegisterType tppRefProductRegisterType =
                tppRefProductRegisterTypeRepo.findFirstByValue(accountDTO.getRegistryTypeCode());

        if(tppRefProductRegisterType==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Код Продукта " + accountDTO.getRegistryTypeCode() +
                     " не найдено в Каталоге продуктов TppRefProductRegisterTypeRepo для данного типа Регистра.");
        }
        return accountDTO;
    }
}
