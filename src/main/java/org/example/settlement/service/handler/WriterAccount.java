package org.example.settlement.service.handler;

import org.example.settlement.DTO.AccountDTO;
import org.example.settlement.State;
import org.example.settlement.dbentity.Account;
import org.example.settlement.dbentity.AccountPool;
import org.example.settlement.dbentity.TppProductRegister;
import org.example.settlement.dbentity.TppRefProductRegisterType;
import org.example.settlement.repository.AccountPoolRepo;
import org.example.settlement.repository.AccountRepo;
import org.example.settlement.repository.TppProductRegisterRepo;
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
@Qualifier("accountOperatorList")
public class WriterAccount implements UnaryOperator<AccountDTO> {

    public WriterAccount( @Autowired AccountPoolRepo accountPoolRepo,
                          @Autowired TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo,
                          @Autowired TppProductRegisterRepo tppProductRegisterRepo,
                          @Autowired AccountRepo accountRepo) {
        this.accountPoolRepo = accountPoolRepo;
        this.tppRefProductRegisterTypeRepo = tppRefProductRegisterTypeRepo;
        this.tppProductRegisterRepo = tppProductRegisterRepo;
        this.accountRepo = accountRepo;
    }

    private final AccountPoolRepo accountPoolRepo;
    private final AccountRepo accountRepo;
    private final TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    private final TppProductRegisterRepo tppProductRegisterRepo;

    @Override
    public AccountDTO apply(AccountDTO accountDTO) {
        List<AccountPool> accPool = accountPoolRepo.findAccountPoolsByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(accountDTO.getBranchCode(),
                    accountDTO.getCurrencyCode() , accountDTO.getMdmCode(),
                    accountDTO.getPriorityCode(), accountDTO.getRegistryTypeCode());

        Account acc = accPool.get(0).getAccountList().get(0);
        if(acc != null ) {
            //Account acc = accList.get(0);
            TppRefProductRegisterType tppRefProductRegisterType = tppRefProductRegisterTypeRepo.findFirstByValue(accountDTO.getRegistryTypeCode());

            TppProductRegister tppProductRegister = new TppProductRegister(
                    Long.valueOf(accountDTO.getInstanceId()),
                    tppRefProductRegisterType,
                    acc.getId(),
                    accountDTO.getCurrencyCode(),
                    State.OPEN.name(),
                    acc.getAccount_number()
            );

            accountDTO.setTppProductRegister(tppProductRegisterRepo.save(tppProductRegister));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Не найден номер счета для " +
                           "branchCode = " + accountDTO.getBranchCode() +
                            " currencyCode = " + accountDTO.getCurrencyCode() +
                            " mdmCode = " + accountDTO.getMdmCode() +
                            " priorityCode + " + accountDTO.getPriorityCode() +
                            " registryTypeCode = " + accountDTO.getRegistryTypeCode());
        }
        return accountDTO;
    }
}

