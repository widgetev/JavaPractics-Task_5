package org.example.settlement.DTO.request;

import lombok.Value;

@Value
public class RequestAccountDTO {
    Integer instanceId; //	+	Идентификатор экземпляра продукта
                        // Идентификатор ЭП, к которому привязывается продуктовый регистр

    String	registryTypeCode; //	-	Тип регистра	Тип создаваемого продуктового регистра
    String	accountType;	 //-    Тип счета	Клиентский или внутрибанковский
    String	currencyCode;//	-	Код валюты	3-х значный код валюты
    String	branchCode;//	-	Код филиала	Код филиала
    String	priorityCode;//	-	Код срочности	Всегда «00» для ПП РО ЮЛ
    String	mdmCode;//	-	Id клиента	МДМ код клиента (КЮЛ)
    String	clientCode;//	-	Код клиента Только для ВИП (РЖД, ФПК). Обсуждается с клиентом (есть выбор).
    String	trainRegion;//	-	Регион принадлежности железной дороги	Только для ВИП (РЖД, ФПК)
    String	counter;//	-	Счетчик	Только для ВИП (РЖД, ФПК)
    String	salesCode;//	-	Код точки продаж	Код точки продаж



}
