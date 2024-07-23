package org.example.settlement.service.handler;

import org.example.settlement.DTO.InstanceArrangementDTO;
import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.dbentity.Agreement;
import org.example.settlement.repository.AgreementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Order(20)//по счастливой случайности порядок этй проверки совпадает в обоих случаях
public class InspectorArrangementNumbersDuplicates implements UnaryOperator<InstanceBodyDTO> {
    @Autowired
    AgreementRepo agreementRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
        for (InstanceArrangementDTO arrangement:instanceBodyDTO.getInstanceArrangement()) {
            List<Agreement> agreementRepoByNumber = agreementRepo.findByNumber(arrangement.getNumber());
            if(!agreementRepoByNumber.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Параметр № Дополнительного соглашения (сделки) Number " +
                                arrangement.getNumber() +
                                " уже существует для ЭП с ИД  " +
                                agreementRepoByNumber.get(0).getProductId().getId()+
                                ".");
            }
        }
        return instanceBodyDTO;
    }
}
