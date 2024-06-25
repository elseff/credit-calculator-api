package ru.elseff.credit.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elseff.credit.dto.GetCreditPaymentsRequest;
import ru.elseff.credit.dto.GetCreditPaymentsResponse;
import ru.elseff.credit.service.CreditService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/credits")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditController {

    CreditService creditService;

    @PostMapping
    public GetCreditPaymentsResponse getPayments(@RequestBody @Valid GetCreditPaymentsRequest request) {
        return creditService.calculate(request);
    }
}
