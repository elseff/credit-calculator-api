package ru.elseff.credit.dto;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum PaymentType {
    DIFFERENTIATED("differentiated", "Дифференцированный"),
    ANNUAL("annual", "Аннуитетный");

    private final String title;
    private final String rusTitle;

    PaymentType(String title, String rusTitle) {
        this.title = title;
        this.rusTitle = rusTitle;
    }

    public static Optional<PaymentType> of(String title) {
        if (!StringUtils.hasText(title))
            return Optional.empty();

        return Arrays.stream(values())
                .filter(c -> c.getTitle().equals(title.toLowerCase()))
                .findFirst();
    }
}
