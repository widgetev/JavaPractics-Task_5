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

import java.util.function.UnaryOperator;

@Component
@Order(10)
@Qualifier("instanceUpdateOperations")
public class InspectorProductExists implements UnaryOperator<InstanceBodyDTO> {
    @Autowired
    TppProductRepo tppProductRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
        TppProduct tppProducts = tppProductRepo.findTppProductById(
                Long.valueOf(instanceBodyDTO.getInstanceId())
        );
        if(tppProducts==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Экземпляр продукта с параметром instanceId " + instanceBodyDTO.getInstanceId() +
                            " не найден.  ");
        }
        return instanceBodyDTO;
    }
}
