package org.example.settlement.DTO.request;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Это DTO - model полученная напрямую из тела запроса.
 * Для дальней шей работы оно будет сконвертировано в InstanceBodyDTO
 * Для этого будет использован Mapper
 */
@Value
//@Component
public class RequestInstanceBodyDTO {
    Integer instanceId; //Идентификатор экземпляра продукта //Если есть, то создаётся ДопСоглашение к ЭП (договору).Если NULL/пусто, то создаётся новый ЭП
    String productType; //Тип Экземпляра Продукта //Тип создаваемого экземпляра продукта/договора, нужен для определения типа создаваемого продукта (договор или сделка) Enum (НСО, СМО, ЕЖО, ДБДС, договор)
    String productCode; //Код продукта в каталоге продуктов
                                    // Сущность - Вид договора
                                    //    Ссылка на запись в каталоге продуктов
                                    //    Нужен для определения типов и количества открываемых ПР и типов ЭП (договор ДКО, например)
                                    //    Соответствует в 172_Справочник классов продуктов Банка, поле  value
    String registerType; //Тип регистра //Соответствует InternalId, ID типа регистра в Справочнике 167_Справочник Тип регистра
    String mdmCode; //Код Клиента (mdm) //Ссылка на клиента, с которым заключен договор
    String contractNumber; //Номер договора //Номер договора обслуживания
    Date contractDate; //Дата заключения договора обслуживания
    Integer priority; //Приоритет //Числовое значение, определяющее последовательность расчета %%
    BigDecimal interestRatePenalty;  //Штрафная процентная ставка
    BigDecimal minimalBalance;  //Неснижаемый остаток
    BigDecimal thresholdAmount; //Пороговая сумма
    String accountingDetails; //Реквизиты выплаты //Из логической модели. Реквизиты выплаты
    String rateType; //Выбор ставки в зависимости от суммы //enum, дифференцированная 0 /прогрессивная 1
    BigDecimal taxPercentageRate; //Ставка налогообложения
    BigDecimal technicalOverdraftLimitAmount; //Сумма лимита технического овердрафта
    Integer contractId; //ID Договора //Ссылка на договор обслуживания счета
    String branchCode; //Код филиала //Код отделения, не БИК
    String isoCurrencyCode; // 	Код валюты	//Трехсимвольный код валюты счета в стандарте ISO
    final static String  urgencyCode="00"; //Код срочности договора //Всегда “00”
    Integer referenceCode; //Код точки продаж //идентификатор точки продаж, где можно осуществлять операции внесения
    RequestAdditionalPropertiesDTO additionalPropertiesVip; //массив дополнительных признаков для сегмента КИБ(VIP),
                                                    // добавлять по мере необходимости?
                                                    // на сегодня могут быть переданы 2 пары ключ/значение: "additionalProperties":
    List<RequestInstanceArrangementDTO> instanceArrangement; //массив Доп.Соглашений


}
