package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    // Injecting @Mock objects into CompensationServiceImpl
    @InjectMocks
    private CompensationServiceImpl compensationService;

    // Created a mocked (expected result) version of EmployeeService interface
    @Mock
    private EmployeeService employeeService;

    // Creating a mocked version of CompensationRepository
    @Mock
    private CompensationRepository compensationRepository;

    @Test
    public void testCreateRead() {
        String employeeId = "someEmployeeId";
        int salary = 15;

        // Create mock Employee with setter method
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        // Create mock Compensation with setter methods
        Compensation expectedCompensation = new Compensation();
        expectedCompensation.setEmployee(employee);
        expectedCompensation.setSalary(salary);
        expectedCompensation.setEffectiveDate(LocalDateTime.now());

        // Mock the behavior of the employeeService and compensationRepository
        when(employeeService.read(employeeId)).thenReturn(employee);
        when(compensationRepository.save(any(Compensation.class))).thenReturn(expectedCompensation);
        when(compensationRepository.findByEmployee(eq(employee))).thenReturn(expectedCompensation);

        // Create the compensation using the service layer method
        Compensation createdCompensation = compensationService.create(employeeId, salary);

        // create asserts, verifying created compensation matches expected values
        assertNotNull("Created compensation should not be null", createdCompensation);
        assertEquals(employeeId, createdCompensation.getEmployee().getEmployeeId());
        assertEquals(salary, createdCompensation.getSalary());
        assertNotNull(createdCompensation.getEffectiveDate());

        // Using the read method from compensation service layer
        Compensation readCompensation = compensationService.read(employeeId);

        // read asserts, verifying the read compensation matches the created one
        assertNotNull(readCompensation);
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertEquals(createdCompensation.getSalary(), readCompensation.getSalary());
        assertEquals(createdCompensation.getEffectiveDate(), readCompensation.getEffectiveDate());
    }
}