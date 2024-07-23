package org.example.settlement.DTO;

import org.example.settlement.dbentity.TppProductRegister;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Getter
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AccountDTO {
    @RequiredFields
    final Integer instanceId;         //	+	Идентификатор экземпляра продукта
                                // Идентификатор ЭП, к которому привязывается продуктовый регистр
    final String	registryTypeCode;   //	-	Тип регистра	Тип создаваемого продуктового регистра
    final String	accountType;	    //-    Тип счета	Клиентский или внутрибанковский
    final String	currencyCode;       //	-	Код валюты	3-х значный код валюты
    final String	branchCode;         //	-	Код филиала	Код филиала
    final String	priorityCode;       //	-	Код срочности	Всегда «00» для ПП РО ЮЛ
    final String	mdmCode;            //	-	Id клиента	МДМ код клиента (КЮЛ)
    final String	clientCode;         //	-	Код клиента Только для ВИП (РЖД, ФПК). Обсуждается с клиентом (есть выбор).
    final String	trainRegion;        //	-	Регион принадлежности железной дороги	Только для ВИП (РЖД, ФПК)
    final String	counter;            //	-	Счетчик	Только для ВИП (РЖД, ФПК)
    final String	salesCode;          //	-	Код точки продаж	Код точки продаж

    public AccountDTO(Integer instanceId, String registryTypeCode, String accountType, String currencyCode, String branchCode, String priorityCode, String mdmCode, String clientCode, String trainRegion, String counter, String salesCode) {
        this.instanceId = instanceId;
        this.registryTypeCode = registryTypeCode;
        this.accountType = accountType;
        this.currencyCode = currencyCode;
        this.branchCode = branchCode;
        this.priorityCode = priorityCode;
        this.mdmCode = mdmCode;
        this.clientCode = clientCode;
        this.trainRegion = trainRegion;
        this.counter = counter;
        this.salesCode = salesCode;
    }

    //Нужно что бы внутри передавать
    @Setter @Nullable
    TppProductRegister tppProductRegister;
}
