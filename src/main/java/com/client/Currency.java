package com.client;

public class Currency {

    private String name;
    private int id;

    public Currency(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Currency() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
