package com.an;

public class Account {

    private static long nextId = 1;

    private String name;
    private double balance;
    private String address;
    private long id;

    public Account(
            String name,
            String address
    ) {
        this.name = name;
        this.balance = 0;
        this.address = address;
        this.id = nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
