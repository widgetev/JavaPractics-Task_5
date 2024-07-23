package org.example.settlement.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class ResponseInstanceMessage{
    private Data date;

    @Getter @Setter
    static class Data {
        private String instanceId;
        private List<String> registerId;
        private List<String> supplementaryAgreementId;
    }

    public ResponseInstanceMessage(String instanceId, List<String> registerId, List<String> supplementaryAgreementId){
        this.date = new Data();
        this.date.setInstanceId(instanceId);
        this.date.setRegisterId(registerId);
        this.date.setSupplementaryAgreementId(supplementaryAgreementId);
    }
}
