package com.mindex.challenge.service.impl;


import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    // Injecting @Mock objects into ReportingStructureServiceImpl
    @InjectMocks
    private ReportingStructureServiceImpl reportingStructureService;

    // Creating a mocked (expected result) version of EmployeeRepository
    @Mock
    private EmployeeRepository employeeRepository;

    // Creating a mocked version of EmployeeService
    @Mock
    private EmployeeService employeeService;



    @Test
    public void testRead(){

        // Creating employee object to have inserted directReports
        String employeeId = "someEmployeeId";
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        /* Creating Employees with direct reports. Could have used loop for creating, but wanted to foil out for
        readability purposes */
        Employee directReport1 = new Employee();
        directReport1.setEmployeeId("report1");

        Employee directReport2 = new Employee();
        directReport2.setEmployeeId("report2");

        Employee directReport3 = new Employee();
        directReport3.setEmployeeId("report3");

        // Adding the direct reports to the list
        List<Employee> directReports = new ArrayList<>();
        directReports.add(directReport1);
        directReports.add(directReport2);
        directReports.add(directReport3);

        // Setting the list of direct reports to the employee's directReports
        employee.setDirectReports(directReports);

        // when clauses for mocking the behavior, essentially the expected results
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);
        when(employeeService.fillOutDirectReport(employee)).thenReturn(employee);
        when(employeeRepository.findByEmployeeId("report1")).thenReturn(directReport1);
        when(employeeRepository.findByEmployeeId("report2")).thenReturn(directReport2);
        when(employeeRepository.findByEmployeeId("report3")).thenReturn(directReport3);

        // Method directly from service layer, using read() that contains calculateNumberOfReports
        ReportingStructure result = reportingStructureService.read(employeeId);

        // Assert statements, comparing the mocked employee vs. the actual result
        assertNotNull(result);
        assertEquals(employee, result.getEmployee());
        int expectedNumberOfReports = 3;
        assertEquals(expectedNumberOfReports, result.getNumberOfReports());

    }

}
