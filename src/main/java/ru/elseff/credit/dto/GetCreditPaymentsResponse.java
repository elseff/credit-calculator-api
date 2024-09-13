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
//    @JsonProperty("Платежи")
    List<MonthPayment> payments;
//    @JsonProperty("Ставка")
    Float rate;
//    @JsonProperty("Описание")
    String description;
//    @JsonProperty("Начало периода")
    LocalDate startPeriod;
//    @JsonProperty("Конец периода")
    LocalDate endPeriod;
//    @JsonProperty("Сумма заёма")
    Long amount;
//    @JsonProperty("Тип платежей")
    String paymentType;
//    @JsonProperty("Общая сумма кредита")
    Double totalAmount;
//    @JsonProperty("Переплата в рублях")
    Double overpayment;
//    @JsonProperty("Переплата в процентах")
    Double percentageOverpayment;

    public GetCreditPaymentsResponse() {
        payments = new ArrayList<>();
        rate = 0.0f;
        amount = 0L;
        overpayment = 0.0;
        percentageOverpayment = 0.0;
    }
}
