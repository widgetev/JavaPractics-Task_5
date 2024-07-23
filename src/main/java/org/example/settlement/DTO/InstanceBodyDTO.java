package org.example.settlement.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Это DTO - полученная преобразованием входящено  запроса.
 *  * Для этого будет использован Mapper
 */
@Getter
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString @EqualsAndHashCode
//@Component
public class InstanceBodyDTO {
    @Setter
    Integer instanceId; //Идентификатор экземпляра продукта //Если есть, то создаётся ДопСоглашение к ЭП (договору).Если NULL/пусто, то создаётся новый ЭП
    @RequiredFields
    final String productType; //Тип Экземпляра Продукта //Тип создаваемого экземпляра продукта/договора, нужен для определения типа создаваемого продукта (договор или сделка) Enum (НСО, СМО, ЕЖО, ДБДС, договор)
    @RequiredFields
    final String productCode; //Код продукта в каталоге продуктов
                                    // Сущность - Вид договора
                                    //    Ссылка на запись в каталоге продуктов
                                    //    Нужен для определения типов и количества открываемых ПР и типов ЭП (договор ДКО, например)
                                    //    Соответствует в 172_Справочник классов продуктов Банка, поле  value
    @RequiredFields
    final String registerType; //Тип регистра //Соответствует InternalId, ID типа регистра в Справочнике 167_Справочник Тип регистра
    @RequiredFields
    final String mdmCode; //Код Клиента (mdm) //Ссылка на клиента, с которым заключен договор
    @RequiredFields
    final String contractNumber; //Номер договора //Номер договора обслуживания
    @RequiredFields
    final Date contractDate; //Дата заключения договора обслуживания
    @RequiredFields
    final Integer priority; //Приоритет //Числовое значение, определяющее последовательность расчета %%
    final BigDecimal interestRatePenalty;  //Штрафная процентная ставка
    final BigDecimal minimalBalance;  //Неснижаемый остаток
    final BigDecimal thresholdAmount; //Пороговая сумма
    final String accountingDetails; //Реквизиты выплаты //Из логической модели. Реквизиты выплаты
    final String rateType; //Выбор ставки в зависимости от суммы //enum, дифференцированная 0 /прогрессивная 1
    final BigDecimal taxPercentageRate; //Ставка налогообложения
    final BigDecimal technicalOverdraftLimitAmount; //Сумма лимита технического овердрафта
    @RequiredFields
    final Integer contractId; //ID Договора //Ссылка на договор обслуживания счета
    @RequiredFields
    final String branchCode; //Код филиала //Код отделения, не БИК
    @RequiredFields
    final String isoCurrencyCode; // 	Код валюты	//Трехсимвольный код валюты счета в стандарте ISO
    @RequiredFields
    final static String  urgencyCode="00"; //Код срочности договора //Всегда “00”
    final Integer referenceCode; //Код точки продаж //идентификатор точки продаж, где можно осуществлять операции внесения
    final List<AdditionalPropertiesDTO> additionalPropertiesVip; //массив дополнительных признаков для сегмента КИБ(VIP),
                                                    // добавлять по мере необходимости?
                                                    // на сегодня могут быть переданы 2 пары ключ/значение: "additionalProperties":
    final List<InstanceArrangementDTO> instanceArrangement; //массив Доп.Соглашений

    public String getUrgencyCode(){
        return urgencyCode;
    }


    //Поля для внутренних нужд
    @Setter @Nullable
    List<String> productRegisterTypeList;
    @Setter @Nullable
    List<Long> productRegisterList;


    public InstanceBodyDTO(Integer instanceId, String productType,
                           String productCode, String registerType,
                           String mdmCode, String contractNumber,
                           Date contractDate, Integer priority,
                           BigDecimal interestRatePenalty, BigDecimal minimalBalance,
                           BigDecimal thresholdAmount, String accountingDetails,
                           String rateType, BigDecimal taxPercentageRate,
                           BigDecimal technicalOverdraftLimitAmount, Integer contractId,
                           String branchCode, String isoCurrencyCode,
                           Integer referenceCode, List<AdditionalPropertiesDTO> additionalPropertiesVip,
                           List<InstanceArrangementDTO> instanceArrangement) {
        this.instanceId = instanceId;
        this.productType = productType;
        this.productCode = productCode;
        this.registerType = registerType;
        this.mdmCode = mdmCode;
        this.contractNumber = contractNumber;
        this.contractDate = contractDate;
        this.priority = priority;
        this.interestRatePenalty = interestRatePenalty;
        this.minimalBalance = minimalBalance;
        this.thresholdAmount = thresholdAmount;
        this.accountingDetails = accountingDetails;
        this.rateType = rateType;
        this.taxPercentageRate = taxPercentageRate;
        this.technicalOverdraftLimitAmount = technicalOverdraftLimitAmount;
        this.contractId = contractId;
        this.branchCode = branchCode;
        this.isoCurrencyCode = isoCurrencyCode;
        this.referenceCode = referenceCode;
        this.additionalPropertiesVip = additionalPropertiesVip;
        this.instanceArrangement = instanceArrangement;
    }

}
