package org.example.settlement.service;

import org.example.settlement.DTO.AccountDTO;
import org.example.settlement.DTO.ResponseAccountMessage;
import org.example.settlement.DTO.request.RequestAccountDTO;
import org.example.settlement.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.UnaryOperator;

@Service
public class AccountService implements AccountServiceable{

    private final AccountMapper accountMapper;
    private final  List<UnaryOperator<AccountDTO>> accountOperatorList;
    public AccountService( @Autowired AccountMapper accountMapper,
                           @Autowired List<UnaryOperator<AccountDTO>> accountOperatorList) {
        this.accountMapper = accountMapper;
        this.accountOperatorList = accountOperatorList;
    }


    public Object process(RequestAccountDTO accMsgIn) {

        AccountDTO accountDTO = accountMapper.map(accMsgIn);

        accountOperatorList.stream().forEach(x->x.apply(accountDTO));

        return new ResponseAccountMessage(accountDTO.getTppProductRegister().getId().toString());
    }

}
