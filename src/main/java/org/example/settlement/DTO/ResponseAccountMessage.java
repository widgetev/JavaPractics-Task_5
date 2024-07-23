package org.example.settlement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseAccountMessage {
    private Data date;

    @Getter @Setter
    static class Data {
        private String accountId;
    }

    public ResponseAccountMessage(String accountId){
        this.date = new Data();
        this.date.setAccountId(accountId);
    }
}
