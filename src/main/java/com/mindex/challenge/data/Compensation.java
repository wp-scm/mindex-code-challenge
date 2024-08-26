package com.mindex.challenge.data;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Compensation {
    private Employee employee;
    // Using integer, could implement this in different ways (annual rate, hourly rate, monthly rate)
    private int salary;
    // Setting this to the local time of the user for simplicity's sake, could also implement in different ways
    private LocalDateTime effectiveDate;


    // Constructor with parameters
    public Compensation(Employee employee, int salary, LocalDateTime effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    // Default constructor
    public Compensation() {}


    /* Getters and setters for access to the private fields. Could be used later on for data/object access/manipulation
    in the service layer */
    public Employee getEmployee() {
        return employee;
    }


    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
