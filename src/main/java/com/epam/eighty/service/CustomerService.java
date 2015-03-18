package com.epam.eighty.service;

import com.epam.eighty.domain.Customer;

import java.util.List;

/**
 * @author Yauheni_Razhkou
 *
 */
public interface CustomerService {

    /**
     * Retrieves a list of all Customer {@link com.epam.eighty.domain.Customer} entities from the datastore.
     *
     * @return the result list
     */
    List<Customer> getAllCustomers();

    /**
     * Retrieves a list of customer {@link com.epam.eighty.domain.Customer} entities whose names match the given customerName.
     * 
     * @param customerName 
     * @return a list of customers
     */
    List<Customer> getCustomersMatchingName(String customerName);

    /**
     * Retrieves a list of customer {@link com.epam.eighty.domain.Customer} entities  whose questions present in topic with given id.
     *
     * @param id topic id
     * @return a list of customers
     */
    List<Customer> getCustomersByTopicId(Long id);
}
