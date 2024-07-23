package org.example.settlement.service.handler;

import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.dbentity.TppProduct;
import org.example.settlement.repository.TppProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.function.UnaryOperator;

@Component
@Order(40)
@Qualifier("instanceCreateOperations")
public class WriterProduct implements UnaryOperator<InstanceBodyDTO> {
    @Autowired
    TppProductRepo tppProductRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
        TppProduct tppProduct=new TppProduct(
                instanceBodyDTO.getProductType(),
                instanceBodyDTO.getContractNumber(),
                new Timestamp(instanceBodyDTO.getContractDate().getTime()),
                instanceBodyDTO.getPriority(),
                instanceBodyDTO.getInterestRatePenalty(),
                instanceBodyDTO.getThresholdAmount(),
                instanceBodyDTO.getRateType(),
                instanceBodyDTO.getTaxPercentageRate());

            tppProduct = tppProductRepo.save(tppProduct);
            instanceBodyDTO.setInstanceId(tppProduct.getId().intValue());
        return instanceBodyDTO;
    }
}
