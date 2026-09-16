package org.example.model;

public class Contact {

    private int employee;
    private int partner;
    private String method;

    public Contact(int employee, int partner, String method) {
        this.employee = employee;
        this.partner = partner;
        this.method = method;
    }

    public int getEmployee() {
        return employee;
    }

    public int getPartner() {
        return partner;
    }

    public String getMethod() {
        return method;
    }
}
