package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }



    /* Didn't know if this was a direct requirement, noticed some of the fields in the direct reports outputted null
    values when using getDirectReports(), this is to fill in those fields when returning an Employee */
    @Override
    public Employee fillOutDirectReport(Employee employee) {

        LOG.debug("Filling out direct report for employee: {}", employee.getEmployeeId());

        // Getting all the employees who are direct reports for the specified employee
        List<Employee> directReports = employee.getDirectReports();

        // Null check otherwise no need to execute below logic
        if (directReports != null){
            // Creating a new list for Employee that will have all the fields populated
            List<Employee> fullDirectReports = new ArrayList<>();
            // Iterating through each employee's direct report list
            for (Employee individualReport : directReports){
                LOG.debug("Fetching details for direct report with ID: {}", individualReport.getEmployeeId());
                // Fetching all the details from DB repository
                Employee filledInReport = employeeRepository.findByEmployeeId(individualReport.getEmployeeId());
                /* Get the direct report fields of the original employee's direct reports, execute same task again for
                their direct reports, and so on (recursion) */
                filledInReport = fillOutDirectReport(filledInReport);
                // Adding fully populated report to Employee
                fullDirectReports.add(filledInReport);
            }

            // Using Employee class's setter method to fill in the fields
            employee.setDirectReports(fullDirectReports);
        }
        return employee;
    }
}
