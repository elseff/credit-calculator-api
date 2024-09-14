package ru.elseff.credit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCreditPaymentsResponse {

    List<MonthPayment> payments; //Платежи

    Float rate; //Ставка

    String description; //Описание

    LocalDate startPeriod; //Начало периода

    LocalDate endPeriod; //Конец периода

    Long amount; //Сумма заёма

    String paymentType; //Тип платежей

    Double totalAmount; //Общая сумма кредита

    Double overpayment; //Переплата в рублях

    Double percentageOverpayment; //Переплата в процентах

    public GetCreditPaymentsResponse() {
        payments = new ArrayList<>();
        rate = 0.0f;
        amount = 0L;
        overpayment = 0.0;
        percentageOverpayment = 0.0;
    }
}
