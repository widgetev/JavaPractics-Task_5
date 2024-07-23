package org.example.settlement.service.handler;

import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.State;
import org.example.settlement.dbentity.*;
import org.example.settlement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Order(50)
@Qualifier("instanceCreateOperations")
public class WriterProductRegistry implements UnaryOperator<InstanceBodyDTO> {
    @Autowired
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;

    @Autowired
    AccountPoolRepo accountPoolRepo;
    @Autowired
    AccountRepo accountRepo;

    @Override
    public InstanceBodyDTO apply(InstanceBodyDTO instanceBodyDTO) {
        //сначала надо найти счет
        //Счет искать надо через пул счетов, для всех типов сохраненных ранее
        // List<AccountPool> accountPoolList = new ArrayList<>();
        List<Long> productRegisterList = new ArrayList<>();
        for (String registerType:instanceBodyDTO.getProductRegisterTypeList()) {
            List<AccountPool> accountPoolList = accountPoolRepo.findAccountPoolsByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
                    instanceBodyDTO.getBranchCode(), //100
                    instanceBodyDTO.getIsoCurrencyCode(), //810
                    instanceBodyDTO.getMdmCode(),// 0
                    instanceBodyDTO.getUrgencyCode(), //00
                    registerType); //03.012.002_47533_ComSoLd

            List<Account> accountList;
            Long accountId = null;
            String accountNumber = null;
            if (!accountPoolList.isEmpty()) {
                accountList = accountRepo.findAccountsByAccountPoolId(accountPoolList.get(0));

                if (!accountList.isEmpty()) {
                    accountId = accountList.get(0).getId();
                    accountNumber = accountList.get(0).getAccount_number();
                }
            }

            TppRefProductRegisterType tppRefProductRegisterType = tppRefProductRegisterTypeRepo.findByValue(registerType);
            TppProductRegister tppProductRegister = new TppProductRegister(
                    Long.valueOf(instanceBodyDTO.getInstanceId()),
                    tppRefProductRegisterType,
                    accountId,
                    instanceBodyDTO.getIsoCurrencyCode(),
                    State.OPEN.name(),
                    accountNumber
            );

            tppProductRegister = tppProductRegisterRepo.save(tppProductRegister);
            productRegisterList.add(tppProductRegister.getId());
        }
        instanceBodyDTO.setProductRegisterList(productRegisterList); //нужно чтобы результат отправить потом
        return instanceBodyDTO;
    }
}
