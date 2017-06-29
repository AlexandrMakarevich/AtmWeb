package com.command;

import com.google.common.base.Objects;

public class PrintBalanceService {

    private String currency;
    private int balance;

    public PrintBalanceService(String currency, int balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "PrintBalanceService{" +
                "currency='" + currency + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintBalanceService that = (PrintBalanceService) o;
        return balance == that.balance &&
                Objects.equal(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currency, balance);
    }
}
