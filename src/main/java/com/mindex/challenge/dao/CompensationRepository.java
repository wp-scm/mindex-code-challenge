package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/* Interface that accesses the bootstrapped employee JSON data (persistence layer).
Need to set Employee as type for param to access in service layer. JPA naming conventions require "By" in name */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    Compensation findByEmployee(Employee employee);
}
