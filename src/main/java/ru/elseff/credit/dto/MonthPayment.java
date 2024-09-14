package ru.elseff.credit.dto;


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
    Integer index; //Номер месяца

    LocalDate startPeriod; //Начало периода

    LocalDate endPeriod; //Конец периода

    Long debtBalance; //Сумма долга

    Long payment; //Сумма платежа

    Long percentagePart; //Процентная часть

    Long debtPart; //Долговая часть

    Long debtBalanceOnPeriodEnd; //Конец долга на конец периода

    String description; //Описание
}
