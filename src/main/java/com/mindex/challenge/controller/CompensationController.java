package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.impl.CompensationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    // Importing service layer to use methods for accessing manipulated data
    @Autowired
    private CompensationServiceImpl compensationService;



    // Endpoint to create compensation type, supply the employee id and give an integer as the salary
    @PostMapping("/compensation/create/{employeeId}/{salary}")
    public Compensation create(@PathVariable String employeeId, @PathVariable int salary) {
        LOG.debug("Received Compensation create request for id [{}] with salary [{}]", employeeId, salary);

        return compensationService.create(employeeId, salary);
    }


    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received Compensation read request for id [{}]", id);

        // Using the service layer compensation read method
        return compensationService.read(id);
    }


//    @GetMapping("/test")
//    public ResponseEntity<String> testEndpoint() {
//        return ResponseEntity.ok("Test endpoint is working");
//    }


//    @GetMapping("/compensationTest/{id}")
//    public ResponseEntity<String> testEndpoint(@PathVariable String id) {
//        return ResponseEntity.ok("Test response");

//    }


}
