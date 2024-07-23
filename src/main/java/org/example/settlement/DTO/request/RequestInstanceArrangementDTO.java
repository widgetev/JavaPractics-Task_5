package org.example.settlement.DTO.request;

import java.util.Date;

/**
 *
 */
public record   RequestInstanceArrangementDTO(
    String	 generalAgreementId,					//ID доп.Ген.соглашения							//ID доп.Ген.соглашения
    String	 supplementaryAgreementId,				//ID доп.соглашения								//ID доп.соглашения
    String	 arrangementType,						//Тип соглашения								//Enum, НСО/ЕЖО/СМО/ДБДС и тд, см. актуальную ЛМД
    Long  shedulerJobId,							//Идентификатор периодичности учета				//Идентификатор задания/расписания периодичность учета/расчета/выплаты фиксируется в поле name
    String	 number,								//Номер ДС										//Номер ДС
    Date     openingDate,							//Дата начала сделки							//дата заключения сделки (НСО/ЕЖО/СМО/ДБДС)
    Date	 closingDate,							//Дата окончания сделки							 //
    Date	 cancelDate,							//Дата отзыва сделки							 //
    Long	 validityDuration,						//Срок действия сделки							 //
    String	 cancellationReason,					//Причина расторжения							 //
    String	 Status,								//Состояние/статус								 //Статус ДС: закрыт, открыт
    Date	 interestCalculationDate,				//Начисление начинается с (дата) 				 //Начисление начинается с (дата)
    Float	 interestRate,							//Процент начисления на остаток					 //Процент начисления на остаток
    Float	 coefficient,							//Коэффициент 									 //Показатель управления ставкой
    String	 coefficientAction,						//Действие коэффициента							 //Повышающий/понижающий enum +/-
    Float	 minimumInterestRate, 					//Минимум по ставке								 //Минимум по ставке
    Float	 minimumInterestRateCoefficient, 		//Коэффициент по минимальной ставке				 //Коэффициент по минимальной ставке
    String	 minimumInterestRateCoefficientAction,	//Действие коэффициента по минимальной ставке	 //Повышающий/понижающий enum +/-
    Float	 maximalnterestRate, 					//Максимум по ставке							 //Максимум по ставке
    Float	 maximalnterestRateCoefficient, 		//Коэффициент по максимальной ставке			 //Коэффициент по максимальной ставке
    String	 maximalnterestRateCoefficientAction 	//Действие коэффициента по максимальной ставке	 //Повышающий/понижающий enum +/-
)
{}
