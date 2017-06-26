package com.atm_exeption;

public enum ErrorCodes {

    NOT_ENOUGH_MONEY("Not enough money on the account!"),
    NO_CURRENCY("You don't have money on currency");

    private String errorMessage;

    ErrorCodes(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
