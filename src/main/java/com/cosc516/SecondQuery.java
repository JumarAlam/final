package com.cosc516;

public class SecondQuery {
    String c_mktsegment;
    int customers;

    public SecondQuery() {
    }

    public SecondQuery(String c_mktsegment, int customers) {
        this.c_mktsegment = c_mktsegment;
        this. customers = customers;
    }

    public String getC_mktsegment() {
        return this.c_mktsegment;
    }

    public void setC_mktsegment(String c_mktsegment) {
        this.c_mktsegment = c_mktsegment;
    }

    public int getCustomers() {
        return this.customers;
    }

    public void setCustomers(int customers) {
        this.customers = customers;
    }
}