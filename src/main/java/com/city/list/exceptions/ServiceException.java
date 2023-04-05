package com.city.list.exceptions;

import com.google.common.base.MoreObjects;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class ServiceException extends RuntimeException {
    private final ServiceError error;
    private final transient Object[] messageParameters;

    private ServiceException(Builder builder) {
        super(builder.cause);
        this.error = builder.error;
        this.messageParameters = builder.messageParameters;
    }

    public static Builder builder(ServiceError error) {
        return new Builder(error);
    }

    public ServiceError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return error.getMessage(messageParameters);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("error", error)
                .add("message", getMessage())
                .toString();
    }

    public static class Builder {

        private final ServiceError error;
        private Throwable cause;
        private Object[] messageParameters;

        private Builder(ServiceError error) {
            this.error = checkNotNull(error, "Service exception's error cannot be null");
            this.cause = null;
            this.messageParameters = new Object[0];
        }

        public ServiceException build() {
            return new ServiceException(this);
        }
    }
}
