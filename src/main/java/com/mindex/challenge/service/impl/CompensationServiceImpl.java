package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import com.mindex.challenge.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Service
public class CompensationServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    // Importing CompensationRepository to access Employee data from DB
    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;


    public Compensation read(String employeeId) {

        // Fetch the employee details using Employee interface read and EmployeeServiceImpl fillOutDirectReport
        Employee employee = employeeService.read(employeeId);
        employee = employeeService.fillOutDirectReport(employee);

        // Fetch compensation details from the repository if exists
        Compensation compensation = compensationRepository.findByEmployee(employee);

        // If there's no compensation object, create one with default values
        if (compensation == null) {
            compensation = new Compensation(employee, 0, LocalDateTime.now());
        // Set Employee for Compensation using setter method
        } else {
            compensation.setEmployee(employee);
        }

        return compensation;
    }




    public Compensation create(String employeeId, int salary) {
        LOG.debug("Starting create method for employeeId [{}] with salary [{}]", employeeId, salary);

        // Fetch employee
        Employee employee = employeeService.read(employeeId);
        // Null check to see if employee not found with specified id
        if (employee == null) {
            LOG.error("Employee with ID [{}] not found", employeeId);
            throw new IllegalArgumentException("Employee not found");
        }
        LOG.debug("Fetched employee: {}", employee);

        // Fill out direct reports for the employee
        employee = employeeService.fillOutDirectReport(employee);
        LOG.debug("Filled out direct reports for employee: {}", employee);

        // Creating compensation object using setter methods
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(LocalDateTime.now());

        // Persisting the data using save()
        Compensation savedCompensation = compensationRepository.save(compensation);
        LOG.debug("Saved compensation: {}", savedCompensation);

        return savedCompensation;
    }
}
