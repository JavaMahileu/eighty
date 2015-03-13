package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.repository.CustomerRepository;
import com.epam.eighty.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> getAllCustomers() {
        Collection<Customer> customers = customerRepository.findAll().as(Collection.class);
        return customers.stream().filter(customer -> customer.getCount() != null).collect(Collectors.toList());
    }

    @Override
    public List<Customer> getSortedSetOfCustomersByName(final String customerName) {
        return customerRepository.getSortedSetOfCustomersByName(ANY_SYMBOL + customerName + ANY_SYMBOL).getContent();
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
