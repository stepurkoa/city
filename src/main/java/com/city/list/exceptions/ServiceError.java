package com.city.list.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ServiceError {

    // 4xx
    CITY_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "We didn't find anything."),
    IT_IS_NOT_URL(HttpStatus.BAD_REQUEST, "It is not url");

    // 5xx

    private HttpStatus status;
    private String message;

    public String getMessage(Object... parameters) {
        if (parameters == null || parameters.length == 0) {
            return message;
        }

        MessageFormat formatter = new MessageFormat(message, Locale.ROOT);
        return formatter.format(parameters);
    }
}
