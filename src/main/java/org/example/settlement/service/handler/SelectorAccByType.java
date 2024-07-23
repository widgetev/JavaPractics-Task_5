package org.example.settlement.service.handler;

import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.dbentity.TppRefProductRegisterType;
import org.example.settlement.repository.TppRefProductRegisterTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Order(30)
@Qualifier("instanceCreateOperations")
public class SelectorAccByType implements UnaryOperator<InstanceBodyDTO> {
    @Autowired
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
            List<TppRefProductRegisterType> tppRefProductRegisterTypeList = tppRefProductRegisterTypeRepo
                    .findByAccountType_ValueIsAndProductClassCode_Value("Клиентский", instanceBodyDTO.getProductCode());
            if(tppRefProductRegisterTypeList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "КодПродукта" +
                                instanceBodyDTO.getProductCode()+
                                " не найдено в Каталоге продуктов tpp_ref_product_class");
            } else {
                //instanceBodyDTO.getClass().getDeclaredFields()
                //запомнить то что нашли - надо запихнуть в instanceBodyDTO, больше, вроде, некуда
                instanceBodyDTO.setProductRegisterTypeList(tppRefProductRegisterTypeList.stream().map(x->x.getValue()).toList());
            }

        return instanceBodyDTO;
    }
}
