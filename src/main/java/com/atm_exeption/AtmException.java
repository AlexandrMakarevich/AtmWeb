package com.atm_exeption;

public class AtmException extends RuntimeException {

    private ErrorCodes errorCodes;

    public AtmException(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }
}
