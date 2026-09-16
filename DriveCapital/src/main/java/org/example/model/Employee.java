package org.example.model;

public class Employee {

    private String name;
    private int company;

    public Employee(String name, int company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public int getCompany() {
        return company;
    }
}
