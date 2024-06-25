package ru.elseff.credit.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthPayment {
    @JsonProperty("Номер месяца")
    Integer index;
    @JsonProperty("Начало периода")
    LocalDate startPeriod;
    @JsonProperty("Конец периода")
    LocalDate endPeriod;
    @JsonProperty("Сумма долга")
    Long debtBalance;
    @JsonProperty("Сумма платежа")
    Long payment;
    @JsonProperty("Процентная часть")
    Long percentagePart;
    @JsonProperty("Долговая часть")
    Long debtPart;
    @JsonProperty("Конец долга на конец периода")
    Long debtBalanceOnPeriodEnd;
    @JsonProperty("Описание")
    String description;
}
