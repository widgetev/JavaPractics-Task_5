package org.example.settlement.service;

import org.example.settlement.DTO.request.RequestInstanceBodyDTO;

public interface InstanceServiceable {
    Object process(RequestInstanceBodyDTO accMsgIn);
}
