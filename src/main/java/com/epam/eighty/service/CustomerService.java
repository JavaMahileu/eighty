package com.epam.eighty.service;

import com.epam.eighty.domain.Customer;

import java.util.List;

/**
 * @author Yauheni_Razhkou
 *
 */
public interface CustomerService {

    /**
     * Retrieves a set of all Customer {@link com.epam.eighty.domain.Customer} entities from the datastore.
     *
     * @return the result set
     */
    List<Customer> getAllCustomers();

    List<Customer> getSortedSetOfCustomersByName(String customerName);

    /**
     * Retrieves a list of customer {@link com.epam.eighty.domain.Customer} entities  whose questions present in topic with given id .
     *
     * @param id topic id
     * @return a list of customers
     */
    List<Customer> getCustomersByTopicId(Long id);
}
