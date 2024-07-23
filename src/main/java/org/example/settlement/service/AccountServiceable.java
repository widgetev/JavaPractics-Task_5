package org.example.settlement.service;

import org.example.settlement.DTO.request.RequestAccountDTO;

public interface AccountServiceable {
    Object process(RequestAccountDTO accMsgIn);
}
