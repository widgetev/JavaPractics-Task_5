package org.example.settlement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseAccountMessage {
    private Data data;

    @Getter @Setter
    static class Data {
        private String accountId;
    }

    public ResponseAccountMessage(String accountId){
        this.data = new Data();
        this.data.setAccountId(accountId);
    }
}
