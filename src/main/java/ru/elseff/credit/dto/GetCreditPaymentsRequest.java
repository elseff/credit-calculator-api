package ru.elseff.credit.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCreditPaymentsRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата начала кредита должна быть передана")
    LocalDate startDate;

    @NotNull(message = "Сумма кредита должна быть передана")
    @Min(value = 100000, message = "Сумма кредита должна быть больше 100 000 рублей")
    @Max(value = 10000000, message = "Сумма кредита должна быть меньше 10 000 000 рублей")
    Long amount;

    @NotNull(message = "Период кредита должен быть передан")
    @Min(value = 3, message = "Период кредита должен быть больше 3 месяцев")
    @Max(value = 120, message = "Период кредита должен быть меньше 120 месяцев (10 лет)")
    Integer period;

    @NotNull(message = "Тип платежей должен быть передан")
    String paymentType;

    @NotNull(message = "Годовая ставка должна быть передана")
    @Min(value = 5, message = "Годовая ставка должна быть больше 5%")
    @Max(value = 30, message = "Годовая ставка должна быть меньше 30%")
    Float rate;
}
