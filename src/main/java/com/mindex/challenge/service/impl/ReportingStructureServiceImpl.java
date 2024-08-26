package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReportingStructureServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    // Importing EmployeeRepository to access Employee data from DB
    @Autowired
    private EmployeeRepository employeeRepository;

    // Importing EmployeeService for fillOutDirectReport
    @Autowired
    private EmployeeService employeeService;

    /* Method to get the ReportingStructure type to be used in the controller layer. Uses the EmployeeRepository method
    to get the Employee data from the DB */
    public ReportingStructure read(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // Using method below to fill in direct report fields of current Employee
        employee = employeeService.fillOutDirectReport(employee);

        // Using the method below to calculate the number of reports using the Employee object from above
        int numberOfReports = calculateNumberOfReports(employee);

        // Return statement that combines the employee and numberOfReports fields to create our ReportingStructure type
        return new ReportingStructure(employee, numberOfReports);
    }


    /* Method to calculate the number of reports based off of directReports. using Employee as param to import specific
    Employee object's # of directReports */
    private int calculateNumberOfReports(Employee employee) {
        int count = 0;

        // Getting all the employees who are direct reports for the specified employee
        List<Employee> directReports = employee.getDirectReports();

        // If the currently selected employee's report has direct reports
        if (directReports != null) {
            // Iterating through each employee's direct report list
            for (Employee individualReport : directReports) {

                // Load full details for the individual report (filling in null values)
                Employee fullReport = employeeRepository.findByEmployeeId(individualReport.getEmployeeId());

                // Increments by 1 because this is in itself is a direct report
                count++;
                // Recursively nest into each direct report under this current employee's report and add that to count
                count = count + calculateNumberOfReports(fullReport);

                LOG.debug("!debug: directReports [{}]", fullReport);
            }

        }

        LOG.debug("!debug: count for directReports [{}]", count);
        return count;
    }
}
