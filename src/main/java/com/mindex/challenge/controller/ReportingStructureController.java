package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.impl.ReportingStructureServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    // Importing service layer to use methods for accessing manipulated data
    @Autowired
    private ReportingStructureServiceImpl reportingStructureService;


    // Endpoint to get the ReportingStructure type with the employee and numberOfReports fields
    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received ReportingStructure read request for id [{}]", id);

        /* Using the service layer reportingStructure's read method to calculate the ReportingStructure of the specific
        employee given their employeeId */
        return reportingStructureService.read(id);
    }

}
