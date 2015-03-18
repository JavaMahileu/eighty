package com.epam.eighty.service;

import com.epam.eighty.domain.Customer;

import java.util.List;

/**
 * @author Yauheni_Razhkou
 *
 */
public interface CustomerService {

    /**
     * Retrieves a list of all {@link com.epam.eighty.domain.Customer Customer} entities from the datastore.
     *
     * @return the result list
     */
    List<Customer> getAllCustomers();

    /**
     * Retrieves a list of {@link com.epam.eighty.domain.Customer Customer} entities whose names match the given {@code customerName}.
     * 
     * @param customerName 
     * @return a list of customers
     */
    List<Customer> getCustomersMatchingName(String customerName);

    /**
     * Retrieves a list of {@link com.epam.eighty.domain.Customer Customer} entities  whose questions present in topic with the given {@code id}.
     *
     * @param id topic id
     * @return a list of customers
     */
    List<Customer> getCustomersByTopicId(Long id);
}
