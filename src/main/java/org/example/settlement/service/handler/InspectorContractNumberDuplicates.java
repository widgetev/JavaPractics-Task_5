package org.example.settlement.service.handler;

import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.dbentity.TppProduct;
import org.example.settlement.repository.TppProductRepo;
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
@Qualifier("instanceCreateOperations")
public class InspectorContractNumberDuplicates implements UnaryOperator<InstanceBodyDTO> {
    @Autowired
    TppProductRepo tppProductRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
        List<TppProduct> tppProducts = tppProductRepo.findTppProductsByNumber(instanceBodyDTO.getContractNumber());
        if(!tppProducts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Параметр ContractNumber № договора " + instanceBodyDTO.getContractNumber() +
                            " уже существует для ЭП с ИД  "+ tppProducts.get(0).getId() + ".");
        }
        return instanceBodyDTO;
    }
}
