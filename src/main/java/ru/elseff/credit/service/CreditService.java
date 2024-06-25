package ru.elseff.credit.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.elseff.credit.dto.GetCreditPaymentsRequest;
import ru.elseff.credit.dto.GetCreditPaymentsResponse;
import ru.elseff.credit.dto.MonthPayment;
import ru.elseff.credit.dto.PaymentType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditService {

    public GetCreditPaymentsResponse calculate(GetCreditPaymentsRequest request) {
        Integer period = request.getPeriod();
        Long amount = request.getAmount();
        Float rate = request.getRate();
        PaymentType paymentType = PaymentType.of(request.getPaymentType())
                .orElseThrow(() -> new IllegalArgumentException("Некорректный тип платежей!"));

        GetCreditPaymentsResponse response = new GetCreditPaymentsResponse();

        double totalAmount = 0; // общая сумма кредита
        float monthlyRate = rate / (100 * 12); // ежемесячная ставка
        switch (paymentType) {
            case ANNUAL -> {
                double monthlyAmount = (amount * (monthlyRate + (monthlyRate /
                                                                 (Math.pow(1 + monthlyRate, period) - 1)))); // ежемесячный платёж
                double balance = amount; // остаток
                for (int i = 1; i <= period; i++) {
                    LocalDate start = request.getStartDate().plus(i, ChronoUnit.MONTHS);
                    LocalDate end = start.plus(1, ChronoUnit.MONTHS);
                    double percentagePart = balance * monthlyRate; // Процентная часть
                    double debtPart = monthlyAmount - percentagePart;
                    double startBalance = balance;
                    totalAmount += monthlyAmount;
                    balance -= debtPart;

                    response.getPayments().add(
                            MonthPayment.builder()
                                    .index(i) // Месяц
                                    .startPeriod(start) // Дата начала периода
                                    .endPeriod(end) // Дата конца периода
                                    .description("Ежемесячный платёж")
                                    .debtBalance(Math.round(startBalance)) // Остаток долга
                                    .debtBalanceOnPeriodEnd(Math.round(balance)) // Остаток долга на конец периода
                                    .payment(Math.round(monthlyAmount)) // Сумма платежа
                                    .percentagePart(Math.round(percentagePart)) // Процентная часть
                                    .debtPart(Math.round(debtPart)) // Долговая часть
                                    .build()
                    );
                }
            }
            case DIFFERENTIATED -> {
                long debtPart = amount / period; // Долговая часть
                double balance = amount; // остаток
                for (int i = 1; i <= period; i++) {
                    LocalDate start = request.getStartDate().plus(i, ChronoUnit.MONTHS);
                    LocalDate end = start.plus(1, ChronoUnit.MONTHS);
                    double percentagePart = balance * monthlyRate; // Процентная часть
                    double payment = percentagePart + debtPart; // Ежемесячный платёж
                    totalAmount += payment;
                    double startBalance = balance;
                    balance -= debtPart;

                    response.getPayments().add(
                            MonthPayment.builder()
                                    .index(i) // Месяц
                                    .startPeriod(start) // Дата начала периода
                                    .endPeriod(end) // Дата конца периода
                                    .description("Ежемесячный платёж")
                                    .debtBalance(Math.round(startBalance)) // Остаток долга
                                    .debtBalanceOnPeriodEnd(Math.round(balance)) // Остаток долга на конец периода
                                    .payment(Math.round(payment)) // Сумма платежа
                                    .percentagePart(Math.round(percentagePart)) // Процентная часть
                                    .debtPart(debtPart) // Долговая часть
                                    .build()
                    );
                }
            }
        }

        double percentageOverpayment = totalAmount * 100 / amount - 100;
        double overpayment = totalAmount - amount;

        response.setPercentageOverpayment(percentageOverpayment); // переплата в прцоентах
        response.setOverpayment(overpayment); // переплата в рублях
        response.setDescription("График выплат");
        response.setRate(rate); // Процентная ставка (годовая)
        response.setAmount(amount); // Сумма заёма
        response.setTotalAmount(totalAmount); // Общая сумма кредита
        response.setStartPeriod(request.getStartDate()); // Дата начала кредита
        response.setEndPeriod(request.getStartDate().plus(period, ChronoUnit.MONTHS)); // Дата конца кредита
        response.setPaymentType(PaymentType.of(request.getPaymentType()).get().getRusTitle());

        return response;
    }
}
