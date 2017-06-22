package com.command;

import com.google.common.base.Objects;

public class PrintBalance {

    private String currency;
    private int balance;

    public PrintBalance(String currency, int balance) {
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
        return "PrintBalance{" +
                "currency='" + currency + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintBalance that = (PrintBalance) o;
        return balance == that.balance &&
                Objects.equal(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currency, balance);
    }
}
