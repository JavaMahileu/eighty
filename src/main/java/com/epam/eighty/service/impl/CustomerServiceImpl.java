package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.repository.CustomerRepository;
import com.epam.eighty.service.CustomerService;
import com.epam.eighty.utility.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Yauheni_Razhkou
 *
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final String ANY_SYMBOL = ".*";

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        Result<Customer> results = customerRepository.findAll();
        List<Customer> list = new ArrayList<>();
        results
            .forEach(result -> {
                if (result.getCount() != null) {
                    list.add(result);
                }
            }
        );
        return list;
    }

    @Override
    public Set<Customer> getSortedSetOfCustomersByName(final String customerName) {
        return Converter.convertToTreeSet(customerRepository.getSortedSetOfCustomersByName(ANY_SYMBOL + customerName + ANY_SYMBOL));
    }

    @Override
    public List<Customer> getCustomersByTopicId(final Long topicId) {
        List<Customer> customerList  = customerRepository.getCustomersByTopicId(topicId).getContent();

        customerList
            .forEach(customer -> customer.setCountInTopic(customerRepository.getQuestionsInTopicByCustomer(customer.getName(),
                topicId)));
        return customerList;
    }

}
