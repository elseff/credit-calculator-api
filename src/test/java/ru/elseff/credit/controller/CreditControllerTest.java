package ru.elseff.credit.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.elseff.credit.dto.GetCreditPaymentsRequest;
import ru.elseff.credit.dto.GetCreditPaymentsResponse;
import ru.elseff.credit.dto.PaymentType;
import ru.elseff.credit.service.CreditService;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class CreditControllerTest {

    @Autowired
    CreditController creditController;

    @SpyBean
    CreditService creditService;

    @Test
    @DisplayName("Дата начала кредита не передана")
    void whenStartDateIsNull_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setStartDate(null);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Дата начала кредита должна быть передана")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Сумма кредита не передана")
    void whenAmountIsNull_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setAmount(null);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Сумма кредита должна быть передана")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Сумма кредита меньше 100 000 рублей")
    void whenAmountLessThan100000_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setAmount(10L);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Сумма кредита должна быть больше 100 000 рублей")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Сумма кредита больше 10 000 000 рублей")
    void whenAmountGreaterThan10000000_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setAmount(10000001L);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Сумма кредита должна быть меньше 10 000 000 рублей")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Период кредита не передан")
    void whenPeriodIsNull_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setPeriod(null);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Период кредита должен быть передан")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Период кредита меньше 3 месяцев")
    void whenPeriodLessThan3_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setPeriod(1);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Период кредита должен быть больше 3 месяцев")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Период кредита больше 120 месяцев")
    void whenPeriodGreaterThan120_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setPeriod(121);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Период кредита должен быть меньше 120 месяцев (10 лет)")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Тип платежей не передан")
    void whenPaymentTypeIsNull_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setPaymentType(null);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Тип платежей должен быть передан")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Годовая ставка не передана")
    void whenRateIsNull_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setRate(null);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Годовая ставка должна быть передана")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Годовая ставка меньше 5%")
    void whenRateLessThan5_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setRate(1f);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Годовая ставка должна быть больше 5%")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Годовая ставка больше 30%")
    void whenGreaterThan30_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setRate(31f);

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw ConstraintViolationException but didn't it")
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContainingAll("Годовая ставка должна быть меньше 30%")
        );

        verifyNoInteractions(creditService);
    }

    @Test
    @DisplayName("Некорректный тип платежей")
    void whenPaymentTypeIsIncorrect_ShouldThrowConstraintViolationException() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();
        request.setPaymentType("illegal");

        assertAll(
                () -> assertThatThrownBy(
                        () -> creditController.getPayments(request)
                ).describedAs("Expected credit controller to throw IllegalArgumentException but didn't it")
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContainingAll("Некорректный тип платежей!")
        );

        verify(creditService, times(1)).calculate(any(GetCreditPaymentsRequest.class));
        verifyNoMoreInteractions(creditService);
    }

    @Test
    @DisplayName("Успешный ответ")
    void successCalculateCreditPayments_ShouldReturnSuccessResponse() {
        GetCreditPaymentsRequest request = getGetCreditPaymentsRequest();

        GetCreditPaymentsResponse actual = creditController.getPayments(request);
        GetCreditPaymentsResponse expected = getGetCreditPaymentsResponse();

        assertAll(
                () -> assertThat(actual)
                        .usingRecursiveComparison(
                                RecursiveComparisonConfiguration.builder()
                                        .withIgnoreCollectionOrder(true)
                                        .build())
                        .ignoringFields("payments")
                        .isEqualTo(expected)
                        .describedAs("Actual response doesn't match expected"),
                () -> assertThat(actual.getPayments().size())
                        .isEqualTo(3)
                        .describedAs("Actual payments size doesn't match expected")
        );
    }

    private GetCreditPaymentsResponse getGetCreditPaymentsResponse() {
        return GetCreditPaymentsResponse.builder()
                .rate(5.0f)
                .description("График выплат")
                .startPeriod(LocalDate.now())
                .endPeriod(LocalDate.now().plusMonths(3))
                .amount(100000L)
                .paymentType(PaymentType.ANNUAL.getRusTitle())
                .totalAmount(100833.158480855)
                .overpayment(833.158480854996)
                .percentageOverpayment(0.8331584808550048)
                .build();
    }

    private GetCreditPaymentsRequest getGetCreditPaymentsRequest() {
        return GetCreditPaymentsRequest.builder()
                .startDate(LocalDate.now())
                .rate(5.0f)
                .paymentType(PaymentType.ANNUAL.getTitle())
                .period(3)
                .amount(100000L)
                .build();
    }
}