package com.mindex.challenge.data;

import java.util.List;

// Creating a separate class apart from Employee for ease of understanding and maintenance

public class ReportingStructure {

    // Declaration of new fields, private to ensure encapsulation
    private Employee employee;
    private int numberOfReports;


    // Constructor for flexibility/reusability to create ReportingStructure objects
    public ReportingStructure(Employee employee, int numberOfReports) {
        // These params are assigned to class's fields
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }


    /* Getters and setters for access to the private fields. Could be used later on for data/object access/manipulation
    in the service layer */
    public Employee getEmployee() {
        return employee;
    }


    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }



}
